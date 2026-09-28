package se.lexicon.mapper;


import org.springframework.stereotype.Component;
import se.lexicon.dto.PromotionResponse;
import se.lexicon.entity.Promotion;

@Component
public class PromotionMapper {

    public PromotionResponse toPromotionResponse(Promotion promotion) {
        return new PromotionResponse(
                promotion.getId(),
                promotion.getCode(),
                promotion.getDiscountPercent(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );
    }
}
