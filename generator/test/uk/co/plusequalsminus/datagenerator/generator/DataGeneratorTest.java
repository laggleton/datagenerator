package uk.co.plusequalsminus.datagenerator.generator;

import org.junit.jupiter.api.Test;

class DataGeneratorTest {

	@Test
	void testGenerateObjects() {
		int numberOfObjects = 100;
		DataGenerator.generateObjects(numberOfObjects);
	}
	
	@Test
	void printResults() {
		DataGenerator.printFiles();
	}
	
	

}
