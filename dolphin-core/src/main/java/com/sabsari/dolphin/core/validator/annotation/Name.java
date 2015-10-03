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

import com.sabsari.dolphin.core.validator.constraint.NameValidator;

@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy=NameValidator.class)
@Documented
public @interface Name {
	String message() default "Invalid name format(1~32 length limited)";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    
    boolean mandatory() default false;
}
