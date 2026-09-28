package se.lexicon.mapper;

import org.springframework.stereotype.Component;
import se.lexicon.dto.OrderItemRequest;
import se.lexicon.dto.OrderItemResponse;
import se.lexicon.dto.OrderRequest;
import se.lexicon.dto.OrderResponse;
import se.lexicon.entity.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Component
public class OrderMapper {

    public OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        BigDecimal totalPrice = orderItem.getPriceAtPurchase()
                .multiply(BigDecimal.valueOf(orderItem.getQuantity()));

        return  new OrderItemResponse(
                orderItem.getProduct().getId(),
                orderItem.getProduct().getName(),
                orderItem.getQuantity(),
                orderItem.getPriceAtPurchase(),
                totalPrice
        );
    }

    public OrderResponse toOrderResponse(Order order) {

        // list of all items in order
        List<OrderItemResponse> items = order.getItems().stream()
                .map(this::toOrderItemResponse)
                .toList();

        // calculates total price
        BigDecimal totalPrice = items.stream()
                .map(OrderItemResponse::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return  new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName(),
                order.getOrderDate(),
                order.getStatus(),
                totalPrice,
                items
        );
    }

    public Order toEntity(OrderRequest request, Customer customer, Map<Long, Product> products, Map<Long, BigDecimal> pricesAtPurchase) {
        // since order service would use placeOrder, status on start would be CREATED.
        // Sets the needed parts of the order and loads each item request into the order to populate with
        // the items purchased
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(Instant.now());
        order.setStatus(OrderStatus.CREATED);

        for (OrderItemRequest itemRequest : request.items()) {
            Product product = products.get(itemRequest.productId());

            OrderItem item = new OrderItem();
            item.setProduct(products.get(itemRequest.productId()));
            item.setQuantity(itemRequest.quantity());
            item.setPriceAtPurchase(pricesAtPurchase.get(itemRequest.productId()));
            order.addItem(item);
        }
        return order;
    }

}
