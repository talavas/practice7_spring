package shpp.level4.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import shpp.level4.validator.ValidGender;
import shpp.level4.validator.ValidTaxId;

import javax.validation.constraints.*;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidTaxId
public class UserRequestDTO {
    private long id;
    @NotNull
    private String username;

    @NotBlank(message = "{invalid.first.name}")
    @NotNull(message = "{invalid.first.name.null}")
    @Size(min = 3, max = 30, message = "{Size.UserRequestDTO.firstName}")
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
    @Pattern(regexp = "\\d{10}", message = "Tax Id is only digits.")
    private String taxId;
    @ValidGender
    private String gender;

}
