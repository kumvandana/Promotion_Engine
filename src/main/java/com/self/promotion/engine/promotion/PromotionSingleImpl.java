package com.self.promotion.engine.promotion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.self.promotion.engine.model.Param;
import com.self.promotion.engine.model.Sku;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PromotionSingleImpl extends Promotion{

    private static final String promotion_name = "single_skus_offer";

    {
        JSONObject json = (JSONObject) super.jsonObject.get(promotion_name);
        createParams(json);
        this.setCollection();
    }

    private void createParams(JSONObject json) {
        priority = json.getInt("priority");
        JSONArray jsonArray = (JSONArray) json.get("skus");
        params = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject  js = jsonArray.getJSONObject(i);
                String name = js.keySet().stream().findFirst().get();
                double value = js.getDouble(js.keySet().stream().findFirst().get());
                this.params.add(new Param(name, value));

            }
        }
        this.setCollection();
    }

    @JsonProperty("priority")
    private int priority;

    @JsonProperty("skus")
    private List<Param> params;

    private Collection collection;

    private int units;

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public Sku addOfferParamsIfApplicable(Sku s) {

        UnitPair unitPair = null;
        List<UnitPair> col = (List<UnitPair>)this.collection;
        col.forEach(c -> {
           if(c.getName().equalsIgnoreCase(s.getName())){
               attachOffer(c, s);
           }
        });
        return s;
    }

    private void attachOffer(UnitPair c, Sku s) {

        s.setPromotion_price(c.getPrice());
        s.setPromotion_units(s.getCost_price_units() >= c.getUnits() ? s.getCost_price_units() - (s.getCost_price_units()%c.getUnits()) : 0);
        s.setOfferApplied(true);

        if(s.isOfferApplied()){
            log.info("Offer {} has been applied to SKU {}", promotion_name, s.getName());
        }
    }

    @Override
    Collection getCollection() {
        return this.collection;
    }

    void setCollection() {

        this.collection = new ArrayList();
        params.forEach( p -> {

            int n = Integer.parseInt(p.getName().split("")[0]);
            String sku = p.getName().split("")[1];
            Double price = p.getPrice()/n;
            this.units = n;
            ((List<UnitPair>)this.collection).add(new UnitPair(sku,price,n));
            }
        );
    }

    @AllArgsConstructor
    @Setter
    @Getter
    class UnitPair {

        private String name;
        private double price;
        private int units;
    }
}