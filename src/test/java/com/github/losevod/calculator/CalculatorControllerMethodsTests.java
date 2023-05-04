package com.github.losevod.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CalculatorControllerMethodsTests {
	@Autowired
	private CalculatorController cc;

	private List<String> list;
	private List<String> list1;
	private List<String> list2;
	private List<String> list3;
	private List<String> list4;

	@Test
	public void shouldProperlyParseString() {
		//given
		String str = "-192 * (-29 / 25) + 47 - 24 * 49";
		String str1 = "(322+19) * 15";
		String str2 = "-123+95+ 152+ -656";
		String str3 = "sqrt_(4.0 + 4) * 3 + 1";
		String str4 = "1+1";

		//when
		list = cc.parseTextValue(str);
		list1 = cc.parseTextValue(str1);
		list2 = cc.parseTextValue(str2);
		list3 = cc.parseTextValue(str3);
		list4 = cc.parseTextValue(str4);

		//then
		Assertions.assertEquals(list, Arrays.asList("-192", "*", "(", "-29", "/", "25", ")", "+", "47", "-", "24", "*", "49"));
		Assertions.assertEquals(list1, Arrays.asList("(", "322" , "+", "19", ")", "*", "15"));
		Assertions.assertEquals(list2, Arrays.asList("-123", "+", "95", "+", "152", "+", "-656"));
		Assertions.assertEquals(list3, Arrays.asList("sqrt", "(", "4.0", "+", "4", ")", "*", "3", "+", "1"));
		Assertions.assertEquals(list4, Arrays.asList("1", "+", "1"));
	}

	@Test
	public void shouldProperlyCalculate() {
		/**
		 * Given {@link list}, {@link list1}, {@link list2}, {@link list3}, {@link list4}
		 */

		//when
		BigDecimal result = cc.doMath(list);
		BigDecimal result1 = cc.doMath(list1);
		BigDecimal result2 = cc.doMath(list2);
		BigDecimal result3 = cc.doMath(list3);
		BigDecimal result4 = cc.doMath(list4);

		//then
		Assertions.assertEquals(result, BigDecimal.valueOf(-906.28));
		Assertions.assertEquals(result1, BigDecimal.valueOf(5115.0));
		Assertions.assertEquals(result2, BigDecimal.valueOf(-532.0));
		Assertions.assertEquals(result3, BigDecimal.valueOf(5.0));
		Assertions.assertEquals(result4, BigDecimal.valueOf(-906.28));
	}

}
