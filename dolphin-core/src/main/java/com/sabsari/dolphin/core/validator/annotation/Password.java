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

import com.sabsari.dolphin.core.validator.constraint.PasswordValidator;

@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy=PasswordValidator.class)
@Documented
public @interface Password {
	String message() default "Invalid password (6~24 length)";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    
    boolean mandatory() default false;
}
