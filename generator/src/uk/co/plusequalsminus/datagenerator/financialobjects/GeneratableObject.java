package uk.co.plusequalsminus.datagenerator.financialobjects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Currency;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import uk.co.plusequalsminus.datagenerator.store.ObjectStore;
import uk.co.plusequalsminus.datagenerator.store.StoreOfStores;
import uk.co.plusequalsminus.datagenerator.utilities.ForeignKey;
import uk.co.plusequalsminus.utilities.IdentityService;
import uk.co.plusequalsminus.utilities.StringGenerator;
import uk.co.plusequalsminus.utilities.StringLibrary;

/**
 * Abstract class to allow automated generation of objects and random population of data
 * GeneratableObjects can have referential integrity to other GeneratableObjects by use of
 * the @ForeignKey annotation
 * Fields can be ignored/not populated/reported by using the @Ignorable annotation
 * 
 * @author Lawrence Aggleton
 *
 */
public abstract class GeneratableObject {
	
	protected static final Logger LOGGER = Logger.getLogger( GeneratableObject.class.getName() );
	private Random r = new Random();
	protected String primaryKey = "";
	
	public String getPrimaryKey() { return primaryKey; }
	public void setPrimaryKey(String p) { this.primaryKey = p; }
	
	public boolean checkWithinAllowableRange(String f, Double d) {
		Double max = null;
		Double min = null;

		max = (Double) getMaxFrom(f);
		min = (Double) getMinFrom(f);
		
		if ((null != max) && (max < d)) { return false; }
		if ((null != min) && (min > d)) { return false; }
		return true;
	}
	
	public boolean checkWithinAllowableRange(String f, Integer i) {
		Integer max = null;
		Integer min = null;
		
		max = (Integer) getMaxFrom(f);
		min = (Integer) getMinFrom(f);
		
		if ((null != max) && (max < i)) { return false; }
		if ((null != min) && (min > i)) { return false; }
		return true;
	}
	
	public Object getMaxFrom(Method m) {
		String name = m.getName();
		if (name.startsWith("get") || name.startsWith("set")) {
			String s = name.substring(3).toLowerCase();
			return getMaxFrom(s);
		}
		else {
			LOGGER.info("Can't find field from method " + name);
		}
		return null;
	}
	
	public Object getMinFrom(Method m) {
		String name = m.getName();
		if (name.startsWith("get") || name.startsWith("set")) {
			String s = name.substring(3).toLowerCase();
			return getMinFrom(s);
		}
		else {
			LOGGER.info("Can't find field from method " + name);
		}
		return null;
	}
	
	public Object getMaxFrom(Field f) {
		return getMaxFrom(f.getName());
	}
	
	public Object getMinFrom(Field f) {
		return getMinFrom(f.getName());
	}
	
	public Object getMaxFrom(String s) {
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field: fields) {
			if (field.getName().toLowerCase().contains(s.toLowerCase())) {
				if (field.getName().toLowerCase().startsWith("max")) {
					return getFieldValue(field);
				}
			}
		}
		return null;
	}
	
	public Object getMinFrom(String s) {
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field: fields) {
			if (field.getName().toLowerCase().contains(s.toLowerCase())) {
				if (field.getName().toLowerCase().startsWith("min")) {
					return getFieldValue(field);
				}
			}
		}
		return null;
	}
		
	protected Object getFieldValue(Field f) {
		try {
			return f.get(this);
		}
		catch (IllegalAccessException e) {
			LOGGER.info("Couldn't get " + f.getType() + " from field name " + f.getName());
		}
		return null;
	}
	
	public Object runGetter(Field field, Object o) {
	    for (Method method : this.getClass().getMethods()) {
	        if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
	            if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
	                try {
	                    return method.invoke(o);
	                }
	                catch (IllegalAccessException e) {
	                	System.out.println("Could not determine method: " + method.getName());
	                }
	                catch (InvocationTargetException e) {
	                	System.out.println("Could not determine method: " + method.getName());
	                }
	            }
	        }
	    }
	    return null;
	}

	/**
	 * This class will randomly populate the fields in a GeneratableObject extension
	 * This will only apply to fields with a 'set<FieldName>()' method
	 * This will not apply to any fields that require multiple parameters to set
	 * This does not affect the 'primaryKey' field which is set on construction
	 * 
	 * The fields will be populated as follows:
	 * 1. If field is a String:
	 * 	a. Look up if field has a list of possible options via the StringLibrary
	 * 	b. Populate a random alphanumeric string
	 * 2. If field is a Double or an Integer:
	 * 	a. Lookup if min/max boundaries are set
	 * 	b. Generate a random value either within these boundaries or without
	 * 3. If field is a Boolean, populate random
	 * 4. If field is a Currency, populate with a random Currency
	 */
	public void populateRandomly() {
		for (Method method : this.getClass().getMethods()) {
	        if (method.getName().startsWith("set")) {
	        	String fieldName = method.getName().substring(3).toLowerCase();
	            if (method.getParameterCount() > 1) {
	            	LOGGER.info(method.getName() + " has more than one parameter and cannot be populated randomly " + method.toString());
	            }
	            else {
	            	Parameter p = method.getParameters()[0];
	            	Class<?> c = p.getType();
		            if (c.equals(String.class)) {     
		            	StringLibrary sl = StringLibrary.getInstance();
		            	String rando = sl.getRandom(fieldName);
		            	if (null == rando) { rando = StringGenerator.generateAlphaNumericString(); }
		            	populateRandom(method, rando);
		            }
		            else if (c.equals(Double.class)) {
		            	Double min = (Double) getMinFrom(method);
		            	Double max = (Double) getMaxFrom(method);
		            	Double rand = r.nextDouble();
		            	if (null != min && null != max) { rand *= (max-min); rand += min; }
		            	populateRandom(method, rand);
		            }
		            else if (c.equals(Integer.class)) {
		            	
		            	if (fieldName.equals("identity")) {
		            		populateRandom(method, IdentityService.getInstance().getIdentity(primaryKey));
		            	}
		            	else {           	
			            	Integer min = (Integer) getMinFrom(method);
			            	Integer max = (Integer) getMaxFrom(method);
			            	
			            	if (null != min && null != max) { populateRandom(method, r.nextInt(max-min)); }
			            	else { populateRandom(method, r.nextInt()); };
		            	}
		            }
		            else if (c.equals(Boolean.class)) {
		            	populateRandom(method, r.nextBoolean());
		            }
		            else if (c.equals(Currency.class)) {
		            	Set<Currency> cSet = Currency.getAvailableCurrencies();
		            	int i = r.nextInt(cSet.size());
		            	int count = 0;
		            	
		            	for (Currency ccy : cSet) {
		            		if (i == count) {
		            			populateRandom(method, ccy);
		            		}
		            		count++;
		            	}
		            }
		            else {
		            	LOGGER.info("Method " + method.getName() + " has unknown input type " + c.getName());
		            }
	            }
	        }
	    }
		StoreOfStores.getInstance().getStore(this.getClass()).registerObject(getPrimaryKey(), this);
		checkReferentialIntegrity();
	}
		
	/**
	 * Looks through all fields in a GeneratableObject to see if any have been annotated as @ForeignKey
	 * If so, looks to check if a version of this type already exists in the store
	 * If not, generates a random instance of the object
	 */
	protected void checkReferentialIntegrity() {
		Field[] fieldList = this.getClass().getDeclaredFields();
		
		for(Field f : fieldList) {
			if (f.isAnnotationPresent(ForeignKey.class)) { 
				//LOGGER.info("Field " + f.getName() + " is a Foreign Key to " + f.getAnnotation(ForeignKey.class).type().getName());
				Class<?> c =  f.getAnnotation(ForeignKey.class).type();
				
				ObjectStore os = StoreOfStores.getInstance().getStore(c);
				f.setAccessible(true);
				String pk = null;
				try {
					pk = f.get(this).toString();
				}
				catch (Exception e) {
					// TODO
					continue;
				}
				if (null == os.getObject(pk)) {
					try {
						Constructor<?> con = c.getConstructor();
						GeneratableObject go = (GeneratableObject) con.newInstance();
						go.setPrimaryKey(pk);
						go.populateRandomly();
						//os.registerObject(pk, go);
					}
					catch (Exception e) {
						LOGGER.warning("No simple constructor for class " + c.getName());
					}
				}
			}
		}
	}
	
	private void populateRandom(Method m, String s) {
		try {
			m.invoke(this, s);
		}
		 catch (IllegalAccessException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
         catch (InvocationTargetException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
	}
	
	private void populateRandom(Method m, Double d) {
		try {
			 m.invoke(this, d);
		}
		 catch (IllegalAccessException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
         catch (InvocationTargetException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
	}
	
	private void populateRandom(Method m, Integer i) {
		try {
			m.invoke(this, i);
		}
		 catch (IllegalAccessException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
         catch (InvocationTargetException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
	}
	
	private void populateRandom(Method m, Boolean b) {
		try {
			m.invoke(this, b);
		}
		 catch (IllegalAccessException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
         catch (InvocationTargetException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
	}
	
	private void populateRandom(Method m, Currency c) {
		try {
			m.invoke(this, c);
		}
		 catch (IllegalAccessException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
         catch (InvocationTargetException e) {
         	System.out.println("Could not determine method: " + m.getName());
         }
	}
}
