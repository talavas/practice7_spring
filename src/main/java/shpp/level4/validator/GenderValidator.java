package shpp.level4.validator;

import shpp.level4.constants.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, String> {
    @Override
    public boolean isValid(String gender, ConstraintValidatorContext constraintValidatorContext) {

        return Gender.fromCode(gender) != null;
    }
}
