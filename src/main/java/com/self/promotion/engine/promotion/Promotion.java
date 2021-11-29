package com.self.promotion.engine.promotion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.self.promotion.engine.model.Sku;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public abstract class Promotion {

    protected  JSONObject jsonObject = null;
    {
        try {
            String str = org.apache.commons.io.FileUtils.readFileToString(new File("src/main/resources/promotions.json"), "UTF-8");
            jsonObject = new JSONObject(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract int getPriority();

    public abstract Sku addOfferParamsIfApplicable(Sku s);

    abstract Collection getCollection();

}