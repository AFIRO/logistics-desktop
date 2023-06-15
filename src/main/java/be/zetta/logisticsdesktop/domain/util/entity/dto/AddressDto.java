package be.zetta.logisticsdesktop.domain.util.entity.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class AddressDto {
    @NotBlank(message = "Street may not be empty")
    private String street;
    @NotBlank(message = "Housenumber may not be empty")
    private String houseNumber;
    @NotBlank(message = "Postal code may not be empty")
    private String postalCode;
    @NotBlank(message = "County may not be empty")
    private String country;
}
