package sn.ucad.restou.Entity;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidateCardNumberConstraintValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateCardNumber {
    String message() default "Card number must follow the format ETU-YYYY-NNN(ex: ETU-2024-001)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}