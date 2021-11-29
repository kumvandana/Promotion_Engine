package com.self.promotion.engine.promotion;

import com.self.promotion.engine.model.InputStream;
import com.self.promotion.engine.model.Param;
import com.self.promotion.engine.model.Sku;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PromotionMultipleImpl extends Promotion{

    private int priority;

    private List<Param> params;

    private Collection collection;

    private static final String promotion_name = "multiple_skus_offer";

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

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public Sku addOfferParamsIfApplicable(Sku s) {

        double price = -1;
        List<String> orderedSkus = InputStream.getInputStreams();

        ((List<OrderPair>)this.collection).forEach(c -> {
            attachOffer(s, orderedSkus, c, price);
        });

        return s;
    }

    private void attachOffer(Sku s, List<String> orderedSkus, OrderPair c, double price) {
        List<String> o = c.getSkus();
        if(o.contains(s.getName()) && orderedSkus.containsAll(o) && !s.isOfferApplied()){
            s.setPromotion_units(1);
            s.setPromotion_price(c.getPrice());
            s.setOfferApplied(true);
        }

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

            String[] skus = p.getName().split("\\+");
            int n = skus.length;
            Double price = p.getPrice()/n;
            ((List<OrderPair>)this.collection).add(new OrderPair(Arrays.asList(skus), price));
            }
        );
    }

    @AllArgsConstructor
    @Getter
    @Setter
    class OrderPair {

        private List<String> skus;
        private double price;
    }
}