package be.zetta.logisticsdesktop.domain.transport.mapper;

import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceTemplate;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TrackAndTraceTemplateDto;
import org.springframework.stereotype.Component;

@Component
public class TrackAndTraceMapper {
    public TrackAndTraceTemplateDto toDto(TrackAndTraceTemplate trackAndTraceTemplate) {
        return TrackAndTraceTemplateDto.builder()
                .trackAndTraceTemplateId(trackAndTraceTemplate.getTrackAndTraceTemplateId())
                .prefix(trackAndTraceTemplate.getPrefix())
                .numberOfChars(trackAndTraceTemplate.getNumberOfChars())
                .onlyNumbers(trackAndTraceTemplate.isOnlyNumbers())
                .extraVerification(trackAndTraceTemplate.getExtraVerification())
                .build();
    }

    public TrackAndTraceTemplate toEntity(TrackAndTraceTemplateDto dto) {
        return TrackAndTraceTemplate.builder()
                .trackAndTraceTemplateId(dto.getTrackAndTraceTemplateId())
                .prefix(dto.getPrefix())
                .numberOfChars(dto.getNumberOfChars())
                .onlyNumbers(dto.isOnlyNumbers())
                .extraVerification(dto.getExtraVerification())
                .build();
    }

    public TrackAndTraceTemplate updateEntity(TrackAndTraceTemplate toUpdate, TrackAndTraceTemplateDto updateInfo){
        toUpdate.setExtraVerification(updateInfo.getExtraVerification());
        toUpdate.setNumberOfChars(updateInfo.getNumberOfChars());
        toUpdate.setOnlyNumbers(updateInfo.isOnlyNumbers());
        toUpdate.setPrefix(updateInfo.getPrefix());
        return toUpdate;
    }
}
