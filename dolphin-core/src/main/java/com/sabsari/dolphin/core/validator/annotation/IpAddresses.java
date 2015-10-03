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

import com.sabsari.dolphin.core.validator.constraint.IpAddressesValidator;

@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy=IpAddressesValidator.class)
@Documented
public @interface IpAddresses {
	String message() default "Invalid format ipv4 address among the list";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
    
    boolean mandatory() default false;
}
