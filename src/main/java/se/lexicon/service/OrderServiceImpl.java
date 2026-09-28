package se.lexicon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.OrderItemRequest;
import se.lexicon.dto.OrderRequest;
import se.lexicon.dto.OrderResponse;
import se.lexicon.entity.Customer;
import se.lexicon.entity.Order;
import se.lexicon.entity.Product;
import se.lexicon.exception.KeyNotFoundException;
import se.lexicon.mapper.OrderMapper;
import se.lexicon.repo.CustomerRepository;
import se.lexicon.repo.OrderRepository;
import se.lexicon.repo.ProductRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final Clock clock;
    private final PromotionService promotionService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ProductRepository productRepository,
                            OrderMapper orderMapper,
                            Clock  clock,
                            PromotionService promotionService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.clock = clock;
        this.promotionService = promotionService;
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

        Map<Long, BigDecimal> pricesAtPurchase = new HashMap<>();
        products.forEach((id, product) -> pricesAtPurchase.put(id, priceWithPromotion(product)));

        // 5 - save order with items
        //date according to zone specified
        Instant date = Instant.now(clock);
        Order order = orderMapper.toEntity(orderRequest, customer, products, pricesAtPurchase, date);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    // helper for subtask 4
    private BigDecimal priceWithPromotion(Product product) {
        //Need to find best promotion in case of several running
        // arranges discount percentages of promotions in natural order, larger values last
        BigDecimal bestPromotion = promotionService.calculateDiscount(product);
        BigDecimal priceMultiplier = BigDecimal.ONE.subtract(bestPromotion.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP));

        //sets new lowest price, scale to 2 decimals and rounds up as per standard
        return product.getPrice().multiply(priceMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

}
