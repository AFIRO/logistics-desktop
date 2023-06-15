package be.zetta.logisticsdesktop.domain.transport.service;

import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.mapper.TransportMapper;
import be.zetta.logisticsdesktop.domain.transport.repository.TransportRepository;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.transport.TransportTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportServiceTest {
    @Mock
    private TransportRepository transportRepository;
    @Mock
    private TransportMapper transportMapper;
    @InjectMocks
    private TransportService transportService;

    @Test
    void getAll_happyFlow() {
        Transport listContents = getTransport();
        List<Transport> expected = List.of(listContents);
        when(transportRepository.findAll()).thenReturn(expected);
        List<Transport> actual = transportService.getAll();

        assertThat(actual).containsExactly(listContents);
        verify(transportRepository, times(1)).findAll();
    }

    @Test
    void getById_HappyFlow() {
        Transport expected = getTransport();
        when(transportRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));
        Transport actual = transportService.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(transportRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void getById_NotFound_throws() {
        when(transportRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> transportService.getById(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);
        verify(transportRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createCustomer_happyFlow() {
        Transport expected = getTransport();
        TransportDto startData = getTransportDto();
        when(transportRepository.existsByTransportName(startData.getTransportName())).thenReturn(false);
        when(transportMapper.toEntity(startData)).thenReturn(expected);
        when(transportRepository.save(expected)).thenReturn(expected);

        Transport actual = transportService.createTransport(startData);
        assertThat(actual).isEqualTo(expected);
        verify(transportRepository, times(1)).existsByTransportName(startData.getTransportName());
        verify(transportRepository, times(1)).save(expected);
    }

    @Test
    void createCustomer_alreadyExists_throwsException() {
        Transport expected = getTransport();
        TransportDto startData = getTransportDto();
        when(transportRepository.existsByTransportName(startData.getTransportName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> transportService.createTransport(startData));
        assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED);
        verify(transportRepository, times(1)).existsByTransportName(startData.getTransportName());
        verify(transportRepository, times(0)).save(expected);
    }

    @Test
    void updateTransport_HappyFlow() {
        Transport expected = getTransportNameUpdated();
        TransportDto updateInfo = getTransportDtoNameUpdate();
        Transport startData = getTransport();

        when(transportRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(transportRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(startData));
        when(transportMapper.updateEntity(startData, updateInfo)).thenReturn(expected);
        when(transportRepository.save(expected)).thenReturn(expected);

        Transport actual = transportService.updateTransport(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(transportRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(transportRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(transportMapper, times(1)).updateEntity(startData, updateInfo);
        verify(transportRepository, times(1)).save(expected);
    }

    @Test
    void updateTransport_NotFound_throws() {
        Transport expected = getTransportNameUpdated();
        TransportDto updateInfo = getTransportDtoNameUpdate();
        Transport startData = getTransport();

        when(transportRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> transportService.updateTransport(MedewerkerTestData.TEST_ID, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);

        verify(transportRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(transportRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(transportMapper, times(0)).updateEntity(startData, updateInfo);
        verify(transportRepository, times(0)).save(expected);
    }

    @Test
    void deleteTransport_happyFlow() {
        Transport expected = getTransport();
        when(transportRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(transportRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));

        Transport actual = transportService.deleteTransport(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);

        verify(transportRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(transportRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(transportRepository, times(1)).deleteById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void deleteTransport_notFound_throws() {
        when(transportRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> transportService.deleteTransport(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);

        verify(transportRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(transportRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(transportRepository, times(0)).deleteById(MedewerkerTestData.TEST_ID);
    }

}