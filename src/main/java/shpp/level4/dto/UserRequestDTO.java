package shpp.level4.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import shpp.level4.validator.ValidGender;
import shpp.level4.validator.ValidTaxId;

import javax.validation.constraints.*;
import java.time.LocalDate;
@Data
@Builder
@ValidTaxId
public class UserRequestDTO {

    @Nullable
    private long id;

    @NotBlank(message = "Invalid First Name: Empty first name")
    @NotNull(message = "Invalid First Name: first name is null")
    @Size(min = 3, max = 30, message = "Invalid First Name: Must be of 3 - 30 characters")
    private String firstName;
    @NotBlank(message = "Invalid Second Name: Empty second name")
    @NotNull(message = "Invalid Second Name: second name  is null")
    @Size(min = 3, max = 30, message = "Invalid Second Name: Must be of 3 - 30 characters")
    private String lastName;
    @DateTimeFormat
    @NotNull
    private LocalDate dateOfBirth;
    @Nullable
    @Size(min = 10, max = 10,  message = "Invalid Tax id: Must be of 10 digits")
    private String taxId;
    @ValidGender
    private String gender;

}
