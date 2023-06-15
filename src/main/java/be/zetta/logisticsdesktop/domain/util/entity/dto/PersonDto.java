package be.zetta.logisticsdesktop.domain.util.entity.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonDto {
    private String personId;
    @NotBlank(message = "First name may not be empty")
    private String firstName;
    @NotBlank(message = "Last name may not be empty")
    private String lastName;
    @NotBlank(message = "Phone number may not be empty")
    private String phoneNumber;
    @Email(message = "Email should be valid.")
    @NotBlank(message = "Email may not be empty")
    private String email;
    @NotNull(message = "Address may not be blank")
    private AddressDto address;
}
