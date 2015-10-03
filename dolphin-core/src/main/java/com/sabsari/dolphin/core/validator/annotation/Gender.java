package com.sabsari.dolphin.core.validator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.sabsari.dolphin.core.validator.constraint.GenderValidator;

@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy=GenderValidator.class)
@Documented
public @interface Gender {
	String message() default "Invalid gender value";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    
    boolean mandatory() default false;
}
