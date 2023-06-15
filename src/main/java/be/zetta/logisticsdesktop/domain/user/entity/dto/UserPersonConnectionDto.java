package be.zetta.logisticsdesktop.domain.user.entity.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserPersonConnectionDto {
    @NotNull(message = "Id must be entered.")
    @NotBlank(message = "Id must not be blank.")
    private String applicationUserId;
    @NotNull(message = "Id must be entered.")
    @NotBlank(message = "Id must not be blank.")
    private String PersonId;
}
