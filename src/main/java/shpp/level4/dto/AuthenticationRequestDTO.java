package shpp.level4.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequestDTO {
    @NotNull
    private String userName;
    @NotNull
    private String password;
}
