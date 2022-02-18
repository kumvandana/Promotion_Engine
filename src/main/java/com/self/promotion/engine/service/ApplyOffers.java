package com.self.promotion.engine.service;

import com.self.promotion.engine.model.Sku;
import com.self.promotion.engine.promotion.Promotion;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

@Slf4j
public class ApplyOffers {

    double finalPrice = 0;
    public double getPrice(List<Promotion> promotions, List<Sku> skus){

        log.info("Sorting promotions on the basis of priority!");
        promotions.sort(Comparator.comparing(Promotion::getPriority));
//        skus.sort(Comparator.comparing(SKU::getName));

        log.info("Iterating over every sku and generating the price after all offers applied!");
        skus.forEach(s-> {
                    promotions.forEach(p -> {
                        if(!s.isOfferApplied()) {
                            Sku sku = p.addOfferParamsIfApplicable(s);
                            if (sku.isOfferApplied()) {
                                setFinalPrice(s);
                            }
                        }
                    });
                    if (!s.isOfferApplied()) {
                        log.info("No offers could be applied to {}", s.getName());
                        setFinalPrice(s);
                    }
                }
        );

        return this.finalPrice;
    }

    private void setFinalPrice(Sku s) {
        this.finalPrice += s.calculate();
    }
}