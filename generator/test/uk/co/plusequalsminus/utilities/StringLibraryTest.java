package uk.co.plusequalsminus.utilities;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class StringLibraryTest {
	
	void initialise() {
		ArrayList<String> nameList = new ArrayList<String>();
		nameList.add("Institution");
		nameList.add("Trader");
		StringLibrary.init(nameList);
	}
	
	@Test
	void testStringLibraryTrader() {
		initialise();
		StringLibrary sl = StringLibrary.getInstance();
		
		String randomTrader = sl.getRandom("Trader");
		
		assertTrue(null != randomTrader);
	}
	
	@Test
	void testStringLibraryFalse() {
		initialise();
		StringLibrary sl = StringLibrary.getInstance();
		
		String random = sl.getRandom("Potato");
		
		assertTrue(null == random);
	}

}
