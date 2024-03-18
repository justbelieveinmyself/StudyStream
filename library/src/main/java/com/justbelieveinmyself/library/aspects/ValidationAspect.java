package com.justbelieveinmyself.library.aspects;

import com.justbelieveinmyself.library.exception.InvalidRequestException;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Aspect
public class ValidationAspect {

    @Pointcut("@annotation(com.justbelieveinmyself.library.aspects.ValidateErrors)")
    public void validateResultMethod() {}

    @After("validateResultMethod() && args(..,result)")
    public void afterMethodWithResult(BindingResult result) {
        validateErrors(result);
    }

    private void validateErrors(BindingResult result) {
        if(result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError fieldError : result.getFieldErrors()) {
                errorMessage.append(fieldError.getDefaultMessage()).append("\n");
            }
            throw new InvalidRequestException(errorMessage.toString());
        }
    }

}
