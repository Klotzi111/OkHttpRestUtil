package de.klotzi111.util.OkHttpRestUtil;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * If an interface has this annotation then the interface has no methods that need to be implemented.
 */
@Retention(SOURCE)
@Target(TYPE)
public @interface MarkerInterface {

}
