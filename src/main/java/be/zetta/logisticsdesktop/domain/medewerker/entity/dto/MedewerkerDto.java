package be.zetta.logisticsdesktop.domain.medewerker.entity.dto;

import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class MedewerkerDto {
    @NotNull(message = "Medewerker must always contain a person.")
    private PersonDto person;
    @NotBlank
    private String functie;
}