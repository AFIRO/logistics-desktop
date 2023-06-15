package be.zetta.logisticsdesktop.domain.user.entity.dto;

import be.zetta.logisticsdesktop.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RegisterDto {
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email may not be empty")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Role is required")
    private UserRole role;
}
