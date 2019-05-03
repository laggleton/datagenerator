package uk.co.plusequalsminus.utilities;

import org.junit.jupiter.api.Test;
import uk.co.plusequalsminus.utilities.StringGenerator;

class StringGeneratorTest {
	
	@Test
	void testStringGenerator() {
		String s = StringGenerator.generateAlphaNumericString();
		System.out.println(s);
		
		s = StringGenerator.generateAlphaNumericString(2, 7);
		System.out.println(s);
	}

}
