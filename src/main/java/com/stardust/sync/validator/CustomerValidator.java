package com.stardust.sync.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.stardust.sync.model.Customer;
import com.stardust.sync.model.User;
import com.stardust.sync.service.CustomerService;
import com.stardust.sync.service.UserService;

@Component
public class CustomerValidator implements Validator {
    @Autowired
    private CustomerService customerService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        
        if (customerService.findByName(customer.getName()) != null) {
            errors.rejectValue("name", "Duplicate.customerForm.name");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "business_email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "business_phone", "NotEmpty");
       

    }
}
