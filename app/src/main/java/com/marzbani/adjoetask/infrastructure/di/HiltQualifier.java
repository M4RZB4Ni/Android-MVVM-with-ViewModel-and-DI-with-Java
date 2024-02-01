package com.marzbani.adjoetask.infrastructure.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface HiltQualifier {
    boolean value() default false;
}