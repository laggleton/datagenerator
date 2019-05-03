package uk.co.plusequalsminus.datagenerator.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import uk.co.plusequalsminus.datagenerator.financialobjects.Trade;

/**
 * 
 * @author Lawrence Aggleton
 * 
 * Singleton TradeStore
 *
 */

public class TradeStore implements IObjectStore {
	
	private static TradeStore instance;
	
	private HashMap<String, Trade> trades;
		
	private TradeStore() {
		trades = new HashMap<String, Trade>();
	}
	
	@Override
	public TradeStore getInstance() {
		if (null == instance) {
			instance = new TradeStore();
		}
		return instance;
	}

	@Override
	public Set<?> getPrimaryKeys() {
		return trades.keySet();
	}

	@Override
	public ArrayList<?> getForeignKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Trade> getObjects() {
		// TODO Auto-generated method stub
		return trades.values();
	}

	@Override
	public Collection<Trade> getObjectsFromForeignKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Trade getObjectFromPrimaryKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addTrade(String s, Trade t) {
		trades.put(s,t);
	}

}
