package be.zetta.logisticsdesktop.domain.transport.mapper;

import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.repository.TransportRepository;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.transport.TransportTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransportMapperTest {
    @Mock
    private TrackAndTraceMapper trackAndTraceMapper;
    @Mock
    private PersonMapper personMapper;
    @Mock
    private TransportRepository transportRepository;
    @InjectMocks
    private TransportMapper transportMapper;

    @Test
    void toDto() {
        TransportDto expected = getTransportDto();
        Transport startData = getTransport();
        when(personMapper.toDto(startData.getContacts().get(0))).thenReturn(expected.getContacts().get(0));
        when(trackAndTraceMapper.toDto(startData.getTrackAndTraceTemplate())).thenReturn(expected.getTrackAndTraceTemplate());
        TransportDto actual = transportMapper.toDto(startData);

        assertThat(actual.getTransportId()).isEqualTo(expected.getTransportId());
        assertThat(actual.getTransportName()).isEqualTo(expected.getTransportName());
        assertThat(actual.getTrackAndTraceTemplate()).isEqualTo(expected.getTrackAndTraceTemplate());
        Assertions.assertThat(actual.getContacts()).containsExactly(expected.getContacts().get(0));
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void toEntity() {
        Transport expected = getTransport();
        TransportDto startData = getTransportDto();
        when(personMapper.toEntity(startData.getContacts().get(0))).thenReturn(expected.getContacts().get(0));
        when(trackAndTraceMapper.toEntity(startData.getTrackAndTraceTemplate())).thenReturn(expected.getTrackAndTraceTemplate());
        Transport actual = transportMapper.toEntity(startData);

        assertThat(actual.getTransportId()).isEqualTo(expected.getTransportId());
        assertThat(actual.getTransportName()).isEqualTo(expected.getTransportName());
        assertThat(actual.getTrackAndTraceTemplate()).isEqualTo(expected.getTrackAndTraceTemplate());
        assertThat(actual.getContacts()).containsExactly(expected.getContacts().get(0));
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void updateEntity_Name_happyFlow() {
        Transport expected = getTransportNameUpdated();
        TransportDto updateInfo = getTransportDtoNameUpdate();
        Transport startData = getTransport();

        when(trackAndTraceMapper.updateEntity(startData.getTrackAndTraceTemplate(),updateInfo.getTrackAndTraceTemplate())).thenReturn(expected.getTrackAndTraceTemplate());
        when(personMapper.updateEntity(startData.getContacts().get(0), updateInfo.getContacts().get(0))).thenReturn(expected.getContacts().get(0));
        when(transportRepository.existsByTransportName(updateInfo.getTransportName())).thenReturn(false);
        Transport actual = transportMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getTransportId()).isEqualTo(expected.getTransportId());
        assertThat(actual.getTransportName()).isEqualTo(expected.getTransportName());
        assertThat(actual.getTrackAndTraceTemplate()).isEqualTo(expected.getTrackAndTraceTemplate());
        assertThat(actual.getContacts()).containsExactly(expected.getContacts().get(0));
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void updateEntity_NameNotUnique_throws() {
        TransportDto updateInfo = getTransportDtoNameUpdate();
        Transport startData = getTransport();
        when(transportRepository.existsByTransportName(updateInfo.getTransportName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class,()-> transportMapper.updateEntity(startData, updateInfo));

        assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED);
    }

    @Test
    void updateEntity_updatedContacts_happyFlow() {
        Transport expected = getTransportContactsUpdated();
        TransportDto updateInfo = getTransportDtoContactsUpdated();
        Transport startData = getTransport();

        when(personMapper.updateEntity(startData.getContacts().get(0), updateInfo.getContacts().get(0))).thenReturn(expected.getContacts().get(0));
        when(personMapper.toEntity(updateInfo.getContacts().get(1))).thenReturn(expected.getContacts().get(1));
        when(trackAndTraceMapper.updateEntity(startData.getTrackAndTraceTemplate(),updateInfo.getTrackAndTraceTemplate())).thenReturn(expected.getTrackAndTraceTemplate());
        Transport actual = transportMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getTransportId()).isEqualTo(expected.getTransportId());
        assertThat(actual.getTransportName()).isEqualTo(expected.getTransportName());
        assertThat(actual.getTrackAndTraceTemplate()).isEqualTo(expected.getTrackAndTraceTemplate());
        assertThat(actual.getContacts()).containsExactly(expected.getContacts().get(0), expected.getContacts().get(1));
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void updateEntity_UnknownContacts_throws() {
        Transport expected = getTransportContactsUpdated();
        TransportDto updateInfo = getTransportDtoUnknownContact();
        Transport startData = getTransport();

        when(personMapper.updateEntity(startData.getContacts().get(0), updateInfo.getContacts().get(0))).thenReturn(expected.getContacts().get(0));
        when(trackAndTraceMapper.updateEntity(startData.getTrackAndTraceTemplate(),updateInfo.getTrackAndTraceTemplate())).thenReturn(expected.getTrackAndTraceTemplate());
        Exception exception = assertThrows(IllegalArgumentException.class,()-> transportMapper.updateEntity(startData, updateInfo));

        assertThat(exception.getMessage()).isEqualTo(UNKNOWN_CONTACT_IN_DTO);
    }
}