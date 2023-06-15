package be.zetta.logisticsdesktop.domain.user.entity.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class LoginDto {
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email may not be empty")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
