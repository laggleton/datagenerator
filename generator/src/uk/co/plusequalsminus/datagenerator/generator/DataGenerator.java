/**
 * 
 */
package uk.co.plusequalsminus.datagenerator.generator;

import java.util.ArrayList;

import uk.co.plusequalsminus.datagenerator.financialobjects.Trade;
import uk.co.plusequalsminus.datagenerator.store.ObjectStore;
import uk.co.plusequalsminus.datagenerator.store.StoreOfStores;
import uk.co.plusequalsminus.utilities.StringGenerator;
import uk.co.plusequalsminus.utilities.StringLibrary;


/**
 * @author Lawrence Aggleton
 * 
 * This is the main class for the DataGenerator project
 * The purpose of this project is to allow users to specify a set of relational objects as flat files.
 * These flat files should have relational integrity to each other and can then be used as a test/prototyping data set
 * 
 *
 */
public class DataGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub


	}
	
	public static void printFiles() {
		StoreOfStores sos = StoreOfStores.getInstance();
		for (ObjectStore os : sos.getAllStores()) {
			os.writeObjectsAsSeparatedValues();
		}
	}
	
	public static void generateObjects(int generateObjectCount) {
		initialiseStringLibrary();
		
		for (int i = 0; i < generateObjectCount; i++) {
			Trade t = new Trade(StringGenerator.generateAlphaNumericString(4,4));
			t.populateRandomly();
		}
	
	}
	
	private static void initialiseStringLibrary() {
		ArrayList<String> fieldList = new ArrayList<String>();
		fieldList.add("institution");
		fieldList.add("trader");
		StringLibrary.init(fieldList);
	}

}
