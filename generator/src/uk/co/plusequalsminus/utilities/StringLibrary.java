package uk.co.plusequalsminus.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

/**
 * 
 * @author Lawrence Aggleton
 * 
 * Singleton class to load a series of sample field values from files
 * Expected filepath is resources/examples
 * 
 * Usage: initiate instance by passing a list of field names
 *
 */

public class StringLibrary {
	
	private static StringLibrary instance;
	protected static final Logger LOGGER = Logger.getLogger(StringLibrary.class.getName());
	
	private HashMap<String, ArrayList<String>> library = new HashMap<String, ArrayList<String>>();
	
	private ArrayList<String> fieldNames;
	
	private StringLibrary(ArrayList<String> fieldNames) {
		this.fieldNames = fieldNames;
		loadLibrary();
	}
	
	public static StringLibrary init(ArrayList<String> fieldNames) {
		if (null == instance) {
			instance = new StringLibrary(fieldNames);
		}
		return instance;
	}
	
	public static StringLibrary getInstance() {
		if (null == instance) {
			LOGGER.warning("Initiate Instance first!");
			return null;
		}
		return instance;
	}
	
	private void loadLibrary() {
		for(String s : this.fieldNames) {
			ArrayList<String> al = new ArrayList<String>();
			String filename = "resources/examples/";
			filename += s.toLowerCase();
			filename += "Names.txt";
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filename));
			    String line;
			    while ((line = reader.readLine()) != null) {
			    	al.add(line);
			    }
			    reader.close();
			}
			catch (Exception e) {
			    LOGGER.warning("Exception occurred trying to read " + filename);
			    e.printStackTrace();
			}
			if (!al.isEmpty()) {
				library.put(s, al);
			}
		}
	}
	
	public ArrayList<String> getNames(String field) {
		return library.get(field);
	}
	
	public String getRandom(String field) {
		Random r = new Random();
		ArrayList<String> al = getNames(field);
		if (null != al) {
			return al.get(r.nextInt(al.size()));
		}
		//LOGGER.info("No names stored for field " + field);
		return null;
	}
	
}
