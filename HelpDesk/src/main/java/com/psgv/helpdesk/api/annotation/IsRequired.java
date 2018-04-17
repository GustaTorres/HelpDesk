package com.psgv.helpdesk.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.psgv.helpdesk.api.enums.TypePersistEnum;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsRequired {
	String message();
	TypePersistEnum [] typePersist();
}
