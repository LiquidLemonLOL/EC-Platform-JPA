package se.lexicon.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.dto.PromotionResponse;
import se.lexicon.entity.Product;
import se.lexicon.entity.Promotion;
import se.lexicon.mapper.PromotionMapper;
import se.lexicon.repo.PromotionRepository;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    private static final ZoneId ZONE = ZoneId.of("Europe/Stockholm");

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;
    private final Clock clock;

    public PromotionServiceImpl(PromotionRepository promotionRepository, PromotionMapper promotionMapper, Clock clock) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
        this.clock = clock;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PromotionResponse> getActivePromotions() {
        return promotionRepository.findActiveOnDate(date()).stream()
                .map(promotionMapper::toPromotionResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateDiscount(Product product) {
        LocalDate date = date();
        return product.getPromotions().stream()
                .filter(p -> isActive(p, date))
                .map(Promotion::getDiscountPercent)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    private LocalDate date() {
        return clock.instant().atZone(ZONE).toLocalDate();
    }

    private boolean isActive(Promotion promotion, LocalDate date) {
        return !promotion.getStartDate().isAfter(date)
                && (promotion.getEndDate() == null || !promotion.getEndDate().isBefore(date));
    }

}
