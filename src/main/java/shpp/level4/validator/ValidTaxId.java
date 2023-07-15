package shpp.level4.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TaxIdValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaxId {
    String message() default "Tax is not complete with User data.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
