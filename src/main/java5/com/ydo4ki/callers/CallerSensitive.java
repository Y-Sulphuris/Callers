package com.ydo4ki.callers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation, indicating that the method annotated with it is sensitive to its calling class.
 * <p>
 * This annotation will only work in projects using Java 5 and newer. It is not available
 * for older versions, but this does not affect the compatibility of the library's functionality.
 * </p>
 * @author Sulphuris
 * @since 1.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CallerSensitive {
    /**
     * This parameter indicates the maximum stack depth inspected by the annotated method.
     * Its default value is 2 as this is the depth you need to get the direct caller of the annotated method.
     * @return the maximum stack depth inspected by the annotated method.
     */
    int depth() default 2;
}

