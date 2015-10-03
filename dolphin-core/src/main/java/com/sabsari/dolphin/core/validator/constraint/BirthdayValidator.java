package com.sabsari.dolphin.core.validator.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sabsari.dolphin.core.validator.FormatValidator;
import com.sabsari.dolphin.core.validator.annotation.Birthday;

public class BirthdayValidator implements ConstraintValidator<Birthday, String> {

	private boolean mandatory;  
	
	@Override
	public void initialize(Birthday constraintAnnotation) {
		this.mandatory = constraintAnnotation.mandatory();		
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (!this.mandatory && value == null)
    		return true;
		
		return FormatValidator.isBirthday(value);
	}

}
