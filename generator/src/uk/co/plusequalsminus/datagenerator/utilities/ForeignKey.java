package uk.co.plusequalsminus.datagenerator.utilities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate a Field is a ForeignKey
 * This ForeignKey should have a Class type specified
 * 
 * @author Lawrence Aggleton
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ForeignKey {
	public Class<?> type();
}
