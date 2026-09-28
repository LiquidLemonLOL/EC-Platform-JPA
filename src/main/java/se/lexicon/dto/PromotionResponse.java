package se.lexicon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PromotionResponse(
        Long id,
        String code,
        BigDecimal discountPercent,
        LocalDate startDate,
        LocalDate endDate
) {}
