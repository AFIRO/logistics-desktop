package be.zetta.logisticsdesktop.domain.order.entity.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class TrackAndTraceCodeDto {
    @NotBlank(message = "Track and Trace code may not be blank.")
    @NotNull(message = "Track and Trace code must be sent.")
    private String trackAndTraceCode;
    @NotBlank(message = "Extra code may not be blank.")
    @NotNull(message = "Extra code must be sent.")
    private String extraCode;
}
