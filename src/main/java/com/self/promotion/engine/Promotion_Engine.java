package com.self.promotion.engine;

import com.self.promotion.engine.model.InputStream;
import com.self.promotion.engine.model.Sku;
import com.self.promotion.engine.promotion.Promotion;
import com.self.promotion.engine.service.ApplyOffers;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class Promotion_Engine {

	public static void main(String[] args) throws IOException {

		System.out.println("Enter the number of kind of items to be purchased (like if [A, B, C], then enter 3)!");
		Scanner myObj = new Scanner(System.in);

		int n = myObj.nextInt();

		System.out.println("Enter the items with quantity, like : 3A");

		List<String> items = new ArrayList<>();
		int i = 0;
		while (i < n) {

			Scanner scanner = new Scanner(System.in); // Create a Scanner object
			items.add(scanner.next());
			i++;
		}

		items.forEach(System.out::printf);

		InputStream.setInputStreams(items);

		List<Sku> skus = initialSetters(items);

		ApplyOffers applyOffers = new ApplyOffers();
		List<Promotion> promotions = getAllPromotions();

		log.info("The promotions available are : {}",promotions);

		double finalPrice = applyOffers.getPrice(promotions,skus);
		log.info("The final Price is : {}", finalPrice);
	}

	private static List<Promotion> getAllPromotions() {

		List<Promotion> promotions = new ArrayList<>();
		Reflections reflections = new Reflections(Promotion.class);
		Set<Class<? extends Promotion>> classes = reflections.getSubTypesOf(Promotion.class);

		classes.forEach(c -> {
			try {
				promotions.add((Promotion) Class.forName(c.getName()).newInstance());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		});
		return promotions;
	}

	private static List<Sku> initialSetters(List<String> items) throws IOException {

		List<Sku> skus = new ArrayList<>();

		FileReader reader=new FileReader("src/main/resources/application.properties");
		Properties p=new Properties();
		p.load(reader);

		items.forEach( i -> {

			String name = null;
			int units = 0;
			if(i.split("").length == 2) {
				name = i.split("")[1];
				units = Integer.parseInt(i.split("")[0]);
			}
			else{
				name = i.split("")[0];
				units = 1;
			}
			try {
				skus.add(new Sku(name, Double.parseDouble(p.getProperty(name)), 0, units, 0, false));
			}catch(Exception e){
				log.error("Inputs not added in right pattern, like 1A or 3B");
			}
		});
		return skus;
	}
}
