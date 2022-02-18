package com.self.promotion.engine;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.*;

@Slf4j
public class Promotion_Engine {

    private static final Logger logger = LoggerFactory.getLogger(Promotion_Engine.class);

	public static void main(String[] args) {

		System.out.println("Enter the types of items to be purchased (like if [A, B, C], then enter 3)!");
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
		
		

	}

}
