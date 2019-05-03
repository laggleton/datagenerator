package uk.co.plusequalsminus.datagenerator.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public interface IObjectStore {
	IObjectStore getInstance();
	
	Set<?> getPrimaryKeys();
	ArrayList<?> getForeignKeys();
	
	Collection<?> getObjects();
	
	Collection<?> getObjectsFromForeignKey();
	
	Object getObjectFromPrimaryKey();
}
