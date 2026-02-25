package sn.ucad.restou.Entity;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateCardNumberConstraintValidator implements ConstraintValidator<ValidateCardNumber, String> {

    private static final String REGEX = "^ETU-\\d{4}-\\d{3}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        return value.matches(REGEX);
    }
}
