package com.example;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by apple on 16-10-10.
 */
@Retention(CLASS) @Target(METHOD)
public @interface Get{
    String url();
    Class<?> clazz();
}
