package com.self.promotion.engine.service;

import com.self.promotion.engine.model.InputStream;
import com.self.promotion.engine.model.Sku;
import com.self.promotion.engine.promotion.Promotion;
import com.self.promotion.engine.promotion.PromotionMultipleImpl;
import com.self.promotion.engine.promotion.PromotionSingleImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ApplyOffersTest {

    List<Promotion> promotions = new ArrayList<>();
    List<String> items = new ArrayList<>();

    @Before
    public void setup(){

        //instantiating promotions
        Promotion promotionMultiple = new PromotionMultipleImpl();
        Promotion promotionSingle = new PromotionSingleImpl();
        promotions.add(promotionSingle);
        promotions.add(promotionMultiple);

    }

    @Test
    public void testScenario1(){

        ApplyOffers applyOffers = new ApplyOffers();
        List<Sku> skus = new ArrayList<>();

        Sku A = new Sku("A",50, 0, 5, 0, false);
        Sku B = new Sku("B",30, 0, 5, 0, false);
        Sku C = new Sku("C",20, 0, 1, 0, false);

        skus.add(A);
        skus.add(B);
        skus.add(C);

        //intantiating
        items.add("5A");
        items.add("5B");
        items.add("1C");
        InputStream.setInputStreams(items);

        double price = applyOffers.getPrice(promotions, skus);
        Assert.assertEquals(370, price, 0);
    }

    @Test
    public void testScenario2(){

        ApplyOffers applyOffers = new ApplyOffers();
        List<Sku> skus = new ArrayList<>();

        Sku A = new Sku("A",50, 0, 1, 0, false);
        Sku B = new Sku("B",30, 0, 1, 0, false);
        Sku C = new Sku("C",20, 0, 1, 0, false);

        skus.add(A);
        skus.add(B);
        skus.add(C);

        //intantiating
        items.add("1A");
        items.add("1B");
        items.add("1C");
        InputStream.setInputStreams(items);

        double price = applyOffers.getPrice(promotions, skus);
        Assert.assertEquals(100, price, 0);
    }

}