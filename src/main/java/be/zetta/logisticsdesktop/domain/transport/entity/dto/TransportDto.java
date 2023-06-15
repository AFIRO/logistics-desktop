package be.zetta.logisticsdesktop.domain.transport.entity.dto;

import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class TransportDto {
    private String transportId;
    @NotBlank(message = "Transport name can not be empty.")
    private String transportName;
    private List<PersonDto> contacts;
    private boolean active;
    private TrackAndTraceTemplateDto trackAndTraceTemplate;
}
