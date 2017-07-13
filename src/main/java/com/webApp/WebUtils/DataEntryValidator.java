package com.webApp.WebUtils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webApp.model.User;

@Component
public class DataEntryValidator implements Validator {
	
	String validemails = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";         
    	
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object o, Errors errors) {
		User user = (User) o;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "NotEmpty");
        if (!user.getEmailAddress().matches(validemails)) {
            errors.rejectValue("emailAddress", "Size.user.emailAddress");
        }
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() <=0  ||  user.getPassword().length() > 25) {
	    	errors.rejectValue("password", "Size.password.name");
	    }
		
	}
}