package se.lexicon.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.OrderItemRequest;
import se.lexicon.dto.OrderRequest;
import se.lexicon.dto.OrderResponse;
import se.lexicon.entity.Customer;
import se.lexicon.entity.Order;
import se.lexicon.entity.Product;
import se.lexicon.entity.Promotion;
import se.lexicon.exception.KeyNotFoundException;
import se.lexicon.mapper.OrderMapper;
import se.lexicon.repo.CustomerRepository;
import se.lexicon.repo.OrderRepository;
import se.lexicon.repo.ProductRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        //find customer
        Customer customer = customerRepository.findById(orderRequest.customerId())
                .orElseThrow(() -> new KeyNotFoundException("Customer not found: " +  orderRequest.customerId()));

        // find each product per requested item
        List<Long> productIds = orderRequest.items().stream()
                .map(OrderItemRequest::productId).distinct()
                .toList();

        Map<Long, Product> products = productRepository.findAllById(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // compared to see if any products are missing after finding each requested item
        List<Long> missingProducts = productIds.stream()
                .filter(id -> !products.containsKey(id)).toList();
        if (!missingProducts.isEmpty()) {
            throw new KeyNotFoundException("Product(s) not found: " +  missingProducts);
        }

        // capture current price, follows promotions LocalDate
        LocalDate date = LocalDate.now();
        Map<Long, BigDecimal> pricesAtPurchase = new HashMap<>();
        products.forEach((id, product) -> pricesAtPurchase.put(id, priceWithPromotion(product, date)));

        // 5 - save order with items
        Order order = orderMapper.toEntity(orderRequest, customer, products, pricesAtPurchase);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    // helper for subtask 4
    private BigDecimal priceWithPromotion(Product product, LocalDate date) {
        //Need to find best promotion in case of several running
        // arranges discount percentages of promotions in natural order, larger values last
        BigDecimal bestPromotion = product.getPromotions().stream()
                .filter(p -> !p.getStartDate().isAfter(date)
                && (p.getEndDate() == null || !p.getEndDate().isBefore(date)))
                .map(Promotion::getDiscountPercent)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

        BigDecimal priceMultiplier = BigDecimal.ONE.subtract(bestPromotion.divide(BigDecimal.valueOf(100)));

        //sets new lowest price, scale to 2 decimals and rounds up as per standard
        return product.getPrice().multiply(priceMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

}
