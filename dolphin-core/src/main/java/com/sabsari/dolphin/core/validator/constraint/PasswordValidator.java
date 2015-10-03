package com.sabsari.dolphin.core.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sabsari.dolphin.core.validator.FormatValidator;
import com.sabsari.dolphin.core.validator.annotation.Password;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	private boolean mandatory;
	
	@Override
	public void initialize(Password constraintAnnotation) {
		this.mandatory = constraintAnnotation.mandatory();		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!this.mandatory && value == null)
    		return true;
		
		return FormatValidator.isPassword(value);
	}
}
