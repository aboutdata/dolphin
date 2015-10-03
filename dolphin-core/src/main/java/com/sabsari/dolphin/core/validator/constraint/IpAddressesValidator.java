package com.sabsari.dolphin.core.validator.constraint;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sabsari.dolphin.core.validator.FormatValidator;
import com.sabsari.dolphin.core.validator.annotation.IpAddresses;

public class IpAddressesValidator implements ConstraintValidator<IpAddresses, List<String>> {

	private boolean mandatory;
	
	@Override
	public void initialize(IpAddresses constraintAnnotation) {
		this.mandatory = constraintAnnotation.mandatory();		
	}

	@Override
	public boolean isValid(List<String> values, ConstraintValidatorContext context) {
		if (!this.mandatory && values == null)
    		return true;
		
		for (String ip : values) {
			if (!FormatValidator.isIpV4Addr(ip))
				return false;
		}
		
		return true;
	}
}
