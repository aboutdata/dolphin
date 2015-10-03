package com.sabsari.dolphin.core.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sabsari.dolphin.core.validator.annotation.Gender;

public class GenderValidator implements ConstraintValidator<Gender, String> {

	private boolean mandatory; 
	
	@Override
	public void initialize(Gender constraintAnnotation) {
		this.mandatory = constraintAnnotation.mandatory();		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!this.mandatory && value == null)
    		return true;
		
		return com.sabsari.dolphin.core.member.domain.code.Gender.isValidCode(value);
	}

}
