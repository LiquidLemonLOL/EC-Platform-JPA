package se.lexicon.service;

import se.lexicon.dto.OrderRequest;
import se.lexicon.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest orderRequest);

}
