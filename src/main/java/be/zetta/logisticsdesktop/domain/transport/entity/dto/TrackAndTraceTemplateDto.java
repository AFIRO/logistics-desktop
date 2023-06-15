package be.zetta.logisticsdesktop.domain.transport.entity.dto;

import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceExtraVerification;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class TrackAndTraceTemplateDto {
    private String trackAndTraceTemplateId;
    @Positive(message = "Character count can not be negative.")
    @NotNull(message = "Character counter must be filled in.")
    private int numberOfChars;
    @NotBlank(message = "Prefix must be filled in.")
    private String prefix;
    @NotNull(message = "Only Numbers boolean must be filled in.")
    private boolean onlyNumbers;
    @NotNull(message = "Extra verification must be filled in.")
    private TrackAndTraceExtraVerification extraVerification;
}
