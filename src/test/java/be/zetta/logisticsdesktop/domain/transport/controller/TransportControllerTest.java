package be.zetta.logisticsdesktop.domain.transport.controller;

import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.mapper.TransportMapper;
import be.zetta.logisticsdesktop.domain.transport.service.TransportService;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;

import static be.zetta.logisticsdesktop.domain.customer.CustomerTestData.DTO_ID_NOT_EMPTY;
import static be.zetta.logisticsdesktop.domain.customer.CustomerTestData.ID_MUST_MATCH_ID_IN_DTO;
import static be.zetta.logisticsdesktop.domain.transport.TransportTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransportControllerTest {
    @Mock
    private TransportService transportService;
    @Mock
    private TransportMapper transportMapper;
    @Mock
    private Validator validator;
    @InjectMocks
    private TransportController transportController;

    @Test
    void getAllTransports() {
        TransportDto listContents = getTransportDto();
        List<Transport> serviceReturn = List.of(getTransport());
        List<TransportDto> expected = List.of(listContents);
        when(transportService.getAll()).thenReturn(serviceReturn);
        when(transportMapper.toDto(serviceReturn.get(0))).thenReturn(expected.get(0));
        List<TransportDto> actual = transportController.getAllTransports();

        assertThat(actual).containsExactly(listContents);
        verify(transportService, times(1)).getAll();
    }

    @Test
    void getById() {
        TransportDto expected = getTransportDto();
        Transport serviceReturn = getTransport();
        when(transportService.getById(MedewerkerTestData.TEST_ID)).thenReturn(serviceReturn);
        when(transportMapper.toDto(serviceReturn)).thenReturn(expected);
        TransportDto actual = transportController.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(transportService, times(1)).getById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createTransport_happyflow() {
        TransportDto expectedDto = getTransportDto();
        Transport expected = getTransport();
        TransportDto startData = getTransportDtoNoId();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(transportService.createTransport(startData)).thenReturn(expected);
        when(transportMapper.toDto(expected)).thenReturn(expectedDto);

        TransportDto actual = transportController.createTransport(startData);
        assertThat(actual).isEqualTo(expectedDto);
        verify(transportService, times(1)).createTransport(startData);
    }

    @Test
    void createTransport_IdNotEmpty_throws() {
        TransportDto startData = getTransportDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> transportController.createTransport(startData));
        assertThat(exception.getMessage()).isEqualTo(DTO_ID_NOT_EMPTY);
        verify(transportService, times(0)).createTransport(startData);
    }

    @Test
    void updateTransport_happyflow() {
        Transport expected = getTransportNameUpdated();
        TransportDto expectedDto = getTransportDtoNameUpdate();
        TransportDto updateInfo = getTransportDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(transportService.updateTransport(MedewerkerTestData.TEST_ID, updateInfo)).thenReturn(expected);
        when(transportMapper.toDto(expected)).thenReturn(expectedDto);

        TransportDto actual = transportController.updateTransport(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expectedDto);
        verify(transportService, times(1)).updateTransport(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void updateTransport_IdDoesNotMatch_throws() {
        TransportDto updateInfo = getTransportDtoNameUpdate();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> transportController.updateTransport("WRONG", updateInfo));
        assertThat(exception.getMessage()).isEqualTo(ID_MUST_MATCH_ID_IN_DTO);
        verify(transportService, times(0)).updateTransport(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void deleteTransport() {
        Transport expected = getTransport();
        TransportDto expectedDto = getTransportDto();

        when(transportService.deleteTransport(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        when(transportMapper.toDto(expected)).thenReturn(expectedDto);

        TransportDto actual = transportController.deleteTransport(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expectedDto);
        verify(transportService, times(1)).deleteTransport(MedewerkerTestData.TEST_ID);
    }

}