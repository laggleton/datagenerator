package uk.co.plusequalsminus.datagenerator.store;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

public class StoreOfStores {
	private static StoreOfStores instance;
	protected static final Logger LOGGER = Logger.getLogger( StoreOfStores.class.getName() );
	private HashMap<Class<?>, ObjectStore> stores;
	
	private StoreOfStores() {
		stores = new HashMap<Class<?>,ObjectStore>();
	}
	
	public static StoreOfStores getInstance() {
		if (null == instance) {
			instance = new StoreOfStores();
		}
		return instance;
	}
	
	public void registerStore(Class<?> c, ObjectStore s) {
		stores.put(c,s);
	}
	
	public ObjectStore getStore(Class<?> c) {
		if (stores.containsKey(c)) {
			return stores.get(c);
		}
		ObjectStore os = new ObjectStore(c);
		stores.put(c,  os);
		return os;
	}
	
	public boolean checkStore(Class<?> c) {
		if (stores.containsKey(c)) {
			return true;
		}
		return false;
	}
	
	public Collection<ObjectStore> getAllStores() {
		return stores.values();
	}

}
