package com.chat.application.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.chat.application.domain.User;

public class EmailValidator implements Validator {


	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId",
				"required.emailId", "Email id is required.");
		
	}


	public boolean supports(Class<?> arg0) {
		return User.class.isAssignableFrom(arg0);
	}

}
