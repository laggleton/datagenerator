package uk.co.plusequalsminus.datagenerator.store;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.co.plusequalsminus.datagenerator.annotations.Ignorable;
import uk.co.plusequalsminus.datagenerator.financialobjects.GeneratableObject;

/**
 * Stores GeneratableObjects in a HashMap by primaryKey
 * Allows store to be returned as JSON or printed as separated values
 * @author Lawrence Aggleton
 *
 */
public class GeneratableObjectStore {
	private HashMap<String, GeneratableObject> store;
	private Class<?> objectType;
	protected static final Logger LOGGER = Logger.getLogger(GeneratableObjectStore.class.getName());
	
	public GeneratableObjectStore(Class<?> objectTypes) throws InvalidClassException {
		if (!GeneratableObject.class.isAssignableFrom(objectTypes)) {
			throw new InvalidClassException("Cannot create a GeneratableObjectStore from a non GeneratableObject class: " + objectTypes.getName());
		}
		store = new HashMap<String, GeneratableObject>();
		objectType = objectTypes;
	}
	
	public GeneratableObject getObject(String pk) {
		if (store.containsKey(pk)) {
			return (GeneratableObject) store.get(pk);
		}
		return null;
	}
	
	public void registerObject(String pk, GeneratableObject o) {
		if (!store.containsKey(pk)) {
			store.put(pk, o);
		}
		else {
			LOGGER.warning("Primary key " + pk + " is already in use!");
		}
	}
	
	public ArrayList<GeneratableObject> getAllObjects() {
		return new ArrayList<GeneratableObject>(store.values());
	}
	
	public JSONArray getObjectsAsJSON() {
		JSONArray ja = new JSONArray();
		for (Object o : store.values()) {
			JSONObject jo = new JSONObject(o);
			ja.put(jo);
		}
		return ja;
	}
	
	public Class<?> getObjectType() {
		return objectType;
	}
	
	public void writeObjectsAsSeparatedValues() {
		writeObjectsAsSeparatedValues(',');
	}
	
	/**
	 * Method will writer a header line (including primaryKey) and then iterate through all 
	 * fields in a GeneratableObject to complete the line
	 * Fields can be ignored if annotated by @Ignorable  
	 * Method then works through all objects in store and populated remainder of file
	 * Filename format is output/<time-of-generation>-<objectType>.txt
	 * @param Delimiter - c
	 */
	public void writeObjectsAsSeparatedValues(char c) {

		File file = null;
		FileWriter fileWriter = null;
		PrintWriter writer = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Calendar gc = new GregorianCalendar();
		String[] splitName = getObjectType().getName().split("\\.");
		String filename = "output/" + sdf.format(gc.getTime()) + "-" + splitName[splitName.length - 1] + ".txt";
		
		try {
			file = new File(filename);
			fileWriter = new FileWriter(file);
			// create file if not exists
			if (!file.exists()) {
				file.createNewFile();
			}
			writer = new PrintWriter(fileWriter);
			
			Field[] allFieldNames = getObjectType().getDeclaredFields();
			ArrayList<Field> fieldNames = new ArrayList<Field>();
			
			// Create list of non @Ignorable field names
			for (Field f : allFieldNames) {
				if (!f.isAnnotationPresent(Ignorable.class)) { 
					fieldNames.add(f);
				}
			}
			
			// Print header line
			writer.printf("%s" + c, "primaryKey");
			for (int i = 0; i < fieldNames.size(); i++) {
				if (i+1 == fieldNames.size()) { //last line
					writer.printf("%s" + "%n", fieldNames.get(i).getName());
				}
				else {
					writer.printf("%s" + c, fieldNames.get(i).getName());
				}
			}
			
			// Print Objects
			for (Object o : getAllObjects()) {
				GeneratableObject go = (GeneratableObject) o;
				Object fieldValue;
				writer.printf("%s" + c, go.getPrimaryKey());
				try {
					for (int i = 0; i < fieldNames.size(); i++) {
						Field f = fieldNames.get(i);
						f.setAccessible(true);
						fieldValue = f.get(go);
						if (null == fieldValue) { fieldValue = ""; }
						if (i+1 == fieldNames.size()) { //last line
							writer.printf("%s" + "%n", fieldValue.toString());
						}
						else {
							writer.printf("%s" + c, fieldValue.toString());
						}
					}
				}
				catch (Exception e) {
					LOGGER.warning("Error when accessing object " + go.getPrimaryKey());
					LOGGER.warning(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		catch (IOException ioe) {
			LOGGER.warning("IO Exception on file " + filename);
			LOGGER.warning(ioe.getMessage());
			ioe.printStackTrace();
		} finally {
			// close PrintWriter
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// close FileWriter
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
