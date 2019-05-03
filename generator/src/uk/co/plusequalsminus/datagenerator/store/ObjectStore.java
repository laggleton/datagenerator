package uk.co.plusequalsminus.datagenerator.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import uk.co.plusequalsminus.datagenerator.financialobjects.GeneratableObject;

public class ObjectStore {
	private HashMap<String, Object> store;
	private Class<?> objectType;
	protected static final Logger LOGGER = Logger.getLogger( ObjectStore.class.getName() );
	
	public ObjectStore(Class<?> objectTypes) {
		store = new HashMap<String, Object>();
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
	
	public ArrayList<Object> getAllObjects() {
		return new ArrayList<Object>(store.values());
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

}
