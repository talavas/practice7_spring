package shpp.level4.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import shpp.level4.dto.UserRequestDTO;

import java.time.LocalDate;

class TaxIdValidatorTest {

    private final TaxIdValidator validator = new TaxIdValidator();

    private UserRequestDTO userRequestDTO;

    @BeforeEach
    void setup(){
       userRequestDTO = UserRequestDTO.builder()
                .firstName("Alexander")
                .lastName("Talavas")
                .dateOfBirth(LocalDate.of(1980, 4, 11))
                .taxId("2932110513")
                .gender("MALE")
                .build();

    }

    @Test
    void isValid_ValidTaxId_ReturnsTrue() {
        boolean isValid = validator.isValid(userRequestDTO, null);
        Assertions.assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource({
            "2932110542, Tax id gender code not correct.",
            "2932210517, Tax id code and Birthday date not valid.",
            "2932110514, Tax id control code not correct."
    })
    void isValid_InvalidTaxId_ThrowsException(String invalidTaxId, String expectedErrorMessage) {
        userRequestDTO.setTaxId(invalidTaxId);
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> validator.isValid(userRequestDTO, null)
        );
        String actualErrorMessage = exception.getMessage();
        Assertions.assertEquals(expectedErrorMessage, actualErrorMessage);
    }


}