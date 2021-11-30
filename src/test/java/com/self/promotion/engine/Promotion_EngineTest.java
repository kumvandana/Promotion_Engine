package com.self.promotion.engine;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Promotion_EngineTest {

	Promotion_Engine promotion_engine = new Promotion_Engine();

	@Test
	public void testScenario1() throws IOException {

		String input = "3";
		InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
//		Add the test inputs and try
		promotion_engine.main(new String[0]);

	};
}
