package com.self.promotion.engine.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class InputStream {

    private static List<String> inputStream = null;

    public static void setInputStreams(List<String> input){

        if(inputStream == null) {
            List<String> skuName = new ArrayList<>();

            input.forEach(i -> {

                if(i.split("").length == 2) {
                    skuName.add(i.split("")[1]);
                }
                else{
                    skuName.add(i.split("")[0]);
                }
            });

            inputStream = skuName;
        }
    }

    public static List<String> getInputStreams() {
        return inputStream;
    }
}
