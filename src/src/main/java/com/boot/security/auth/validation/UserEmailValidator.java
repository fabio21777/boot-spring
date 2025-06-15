package com.boot.security.auth.validation;

import com.boot.security.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;

public class UserEmailValidator implements ConstraintValidator<UserEmailValid, String>{

    private final UserRepository userRepository;

    public UserEmailValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UserEmailValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Assert.hasText(email, "Email must not be empty");
        return  !userRepository.existsByEmail(email);
    }
}

