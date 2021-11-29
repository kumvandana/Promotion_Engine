package com.self.promotion.engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class Sku {

	private String name;
    private double cost_price;
    private double promotion_price;
    private int cost_price_units;
    private int promotion_units;
    private boolean offerApplied = false;

    public double calculate(){

        try {

            double price = this.promotion_units * this.promotion_price +
                    (this.cost_price_units - this.promotion_units) * cost_price;
            log.info("The price for {} after offers is {}", this.getName(), price);

            return price;
        }
        catch(Exception e){
            log.info("Error occured while calculating offer price for sku {}. " +
                    "Hence, returning 0.",this.getName());
            return 0;
        }
    }
}
