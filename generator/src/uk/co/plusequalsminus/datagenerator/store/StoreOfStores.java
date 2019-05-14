package uk.co.plusequalsminus.datagenerator.store;

import java.util.Collection;
import java.util.HashMap;

import uk.co.plusequalsminus.datagenerator.financialobjects.GeneratableObject;

/**
 *
 * A Singleton class allowing access to GeneratableObjectStores
 * Allows only one store per class type
 * @author Lawrence Aggleton
 * 
 */
public class StoreOfStores {
	private static StoreOfStores instance;
	private HashMap<Class<?>, GeneratableObjectStore> stores;
	
	private StoreOfStores() {
		stores = new HashMap<Class<?>,GeneratableObjectStore>();
	}
	
	public static StoreOfStores getInstance() {
		if (null == instance) {
			instance = new StoreOfStores();
		}
		return instance;
	}
	
	/**
	 * Gets a GeneratableObjectStore for a given class type
	 * Checks if store already exists, if not creates it
	 * @param Class type of store c
	 * @return Store
	 */
	public GeneratableObjectStore getStore(Class<?> c) throws Exception {
		if (stores.containsKey(c)) {
			return stores.get(c);
		}
		GeneratableObjectStore os = new GeneratableObjectStore(c);
		stores.put(c, os);
		return os;
	}
	
	public Collection<GeneratableObjectStore> getAllStores() {
		return stores.values();
	}

}
