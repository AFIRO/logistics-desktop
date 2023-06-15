package be.zetta.logisticsdesktop.domain.transport.mapper;

import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceTemplate;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TrackAndTraceTemplateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.transport.TransportTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(MockitoExtension.class)
class TrackAndTraceMapperTest {
    @InjectMocks
    private TrackAndTraceMapper trackAndTraceMapper;

    @Test
    void toDto() {
        TrackAndTraceTemplateDto expected = getTrackAndTraceTemplateDto();
        TrackAndTraceTemplateDto actual = trackAndTraceMapper.toDto(getTrackAndTraceTemplate());

        assertThat(actual.getPrefix()).isEqualTo(expected.getPrefix());
        assertThat(actual.getExtraVerification()).isEqualTo(expected.getExtraVerification());
        assertThat(actual.getTrackAndTraceTemplateId()).isEqualTo(expected.getTrackAndTraceTemplateId());
        assertThat(actual.getNumberOfChars()).isEqualTo(expected.getNumberOfChars());
        assertThat(actual.isOnlyNumbers()).isEqualTo(expected.isOnlyNumbers());

    }

    @Test
    void toEntity() {
        TrackAndTraceTemplate expected = getTrackAndTraceTemplate();
        TrackAndTraceTemplate actual = trackAndTraceMapper.toEntity(getTrackAndTraceTemplateDto());

        assertThat(actual.getPrefix()).isEqualTo(expected.getPrefix());
        assertThat(actual.getExtraVerification()).isEqualTo(expected.getExtraVerification());
        assertThat(actual.getTrackAndTraceTemplateId()).isEqualTo(expected.getTrackAndTraceTemplateId());
        assertThat(actual.getNumberOfChars()).isEqualTo(expected.getNumberOfChars());
        assertThat(actual.isOnlyNumbers()).isEqualTo(expected.isOnlyNumbers());
    }

    @Test
    void updateEntity() {
        TrackAndTraceTemplate expected = getTrackAndTraceTemplateUpdated();
        TrackAndTraceTemplateDto updateInfo = getTrackAndTraceTemplateDtoUpdated();
        TrackAndTraceTemplate startData = getTrackAndTraceTemplate();
        TrackAndTraceTemplate actual = trackAndTraceMapper.updateEntity(startData,updateInfo);

        assertThat(actual.getPrefix()).isEqualTo(expected.getPrefix());
        assertThat(actual.getExtraVerification()).isEqualTo(expected.getExtraVerification());
        assertThat(actual.getTrackAndTraceTemplateId()).isEqualTo(expected.getTrackAndTraceTemplateId());
        assertThat(actual.getNumberOfChars()).isEqualTo(expected.getNumberOfChars());
        assertThat(actual.isOnlyNumbers()).isEqualTo(expected.isOnlyNumbers());
    }
}