package com.weride.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.YearMonth;

public class FutureDateValidator implements ConstraintValidator<FutureDate, YearMonth> {
    @Override
    public void initialize(FutureDate futureDate) {
    }

    @Override
    public boolean isValid(YearMonth expirationDate, ConstraintValidatorContext context) {
        if (expirationDate == null) {
            return true; // Let the @NotNull annotation handle null validation
        }

        YearMonth currentMonth = YearMonth.now();
        return expirationDate.isAfter(currentMonth);
    }
}

