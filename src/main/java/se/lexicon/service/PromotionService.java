package se.lexicon.service;

import se.lexicon.dto.PromotionResponse;
import se.lexicon.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface PromotionService {

    List<PromotionResponse> getActivePromotions();

    BigDecimal calculateDiscount(Product product);

}
