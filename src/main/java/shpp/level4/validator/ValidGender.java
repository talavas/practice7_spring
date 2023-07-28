package shpp.level4.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidGender {
    String message() default "{invalid.gender}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
