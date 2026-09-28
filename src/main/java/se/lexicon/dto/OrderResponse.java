package se.lexicon.dto;

import se.lexicon.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        Long customerId,
        String customerName,
        Instant orderDate,
        OrderStatus status,
        BigDecimal total,
        List<OrderItemResponse> items
) {}
