package shpp.level4.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.level4.dto.UserRequestDTO;
import shpp.level4.constants.Gender;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class TaxIdValidator implements ConstraintValidator<ValidTaxId, Object> {

    Logger logger = LoggerFactory.getLogger("console");
    private static final LocalDate START_DATE = LocalDate.of(1899,12,31);

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if(!(object instanceof UserRequestDTO)){
            throw new IllegalArgumentException("@ValidTaxId could apply only on UserRequestDTO.");
        }
        UserRequestDTO user = (UserRequestDTO) object;
        String taxId = user.getTaxId();

        if(taxId != null) {
            logger.debug("taxId = {}", taxId);

            int controlDigit = Integer.parseInt(taxId.substring(9));
            int calculatedControlDigit = calculateControlDigit(taxId);
            if (controlDigit != calculatedControlDigit)
                throw new IllegalArgumentException("Tax id control code not correct.");


            int genderTaxCode = Integer.parseInt(taxId.substring(8, 9));
            Gender genderFromTaxCode = Gender.fromTaxDigit(genderTaxCode);
            logger.debug("Gender {} from tax code = {}", genderFromTaxCode, genderTaxCode);
            Gender userGender = Gender.fromCode(user.getGender());
            logger.debug("User gender = {}", userGender);
            if (!userGender.equals(genderFromTaxCode))
                throw new IllegalArgumentException("Tax id gender code not correct.");

            int days = Integer.parseInt(taxId.substring(0, 5));
            LocalDate birthDate = user.getDateOfBirth();
            LocalDate birthDateFromTaxId = START_DATE.plusDays(days);
            if (!birthDate.equals(birthDateFromTaxId))
                throw new IllegalArgumentException("Tax id code and Birthday date not valid.");
        }
        return true;
    }

    protected static int calculateControlDigit(String taxId) {
        int[] coefficients = { -1, 5, 7, 9, 4, 6, 10, 5, 7 };
        int sum = 0;
        for (int i = 0; i < coefficients.length; i++) {
            int digit = Character.getNumericValue(taxId.charAt(i));
            sum += coefficients[i] * digit;
        }
        int remainder = sum % 11;
        return (remainder == 10) ? 0 : remainder;
    }
}
