package com.weride.dto;

import com.auth0.jwt.interfaces.Payload;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
public @interface FutureDate {
    String message() default "Date should be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

