package com.justbelieveinmyself.authservice.domains.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordMatching, Object> {
    private String password;
    private String confirmPassword;

    @Override
    public void initialize(PasswordMatching constraintAnnotation) {
        password = constraintAnnotation.password();
        confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        String passwordValue = (String) beanWrapper.getPropertyValue(password);
        String confirmPasswordValue = (String) beanWrapper.getPropertyValue(confirmPassword);

        return passwordValue != null && passwordValue.equals(confirmPasswordValue);
    }
}
