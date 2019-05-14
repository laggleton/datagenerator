package uk.co.plusequalsminus.datagenerator.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotation designed to indicate fields to be ignored when parsed
 * by reflection in a Java Object
 * 
 * @author Lawrence Aggleton
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Ignorable {

}
