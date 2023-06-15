package be.zetta.logisticsdesktop.domain.medewerker.service;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.medewerker.mapper.MedewerkerMapper;
import be.zetta.logisticsdesktop.domain.medewerker.repository.MedewerkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedewerkerServiceTest {
    @Mock
    private MedewerkerRepository medewerkerRepository;
    @Mock
    private MedewerkerMapper medewerkerMapper;
    @InjectMocks
    private MedewerkerService medewerkerService;

    @Test
    void getAll_happyFlow() {
        Medewerker listContents = getMedewerker();
        List<Medewerker> expected = List.of(listContents);
        when(medewerkerRepository.findAll()).thenReturn(expected);
        List<Medewerker> actual = medewerkerService.getAll();

        assertThat(actual).containsExactly(listContents);
        verify(medewerkerRepository, times(1)).findAll();
    }

    @Test
    void getById_HappyFlow() {
        Medewerker expected = getMedewerker();
        when(medewerkerRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(expected));
        Medewerker actual = medewerkerService.getById(TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(medewerkerRepository, times(1)).findById(TEST_ID);
    }

    @Test
    void getById_NotFound_throws() {
        when(medewerkerRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> medewerkerService.getById(TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(MEDEWERKER_NOT_FOUND);
        verify(medewerkerRepository, times(1)).findById(TEST_ID);
    }

    @Test
    void createMedewerker_happyFlow() {
        Medewerker expected = getMedewerker();
        MedewerkerDto startData = getMedewerkerDto();
        when(medewerkerRepository.existsMedewerkerByPerson_Email(startData.getPerson().getEmail())).thenReturn(false);
        when(medewerkerMapper.toEntity(startData)).thenReturn(expected);
        when(medewerkerRepository.save(expected)).thenReturn(expected);

        Medewerker actual = medewerkerService.createMedewerker(startData);
        assertThat(actual).isEqualTo(expected);
        verify(medewerkerRepository, times(1)).existsMedewerkerByPerson_Email(startData.getPerson().getEmail());
        verify(medewerkerRepository, times(1)).save(expected);
    }

    @Test
    void createMedewerker_alreadyExists_throwsException() {
        Medewerker expected = getMedewerker();
        MedewerkerDto startData = getMedewerkerDto();
        when(medewerkerRepository.existsMedewerkerByPerson_Email(startData.getPerson().getEmail())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> medewerkerService.createMedewerker(startData));
        assertThat(exception.getMessage()).isEqualTo(MEDEWERKER_ALREADY_REGISTERED_WITH_THIS_EMAIL);
        verify(medewerkerRepository, times(1)).existsMedewerkerByPerson_Email(startData.getPerson().getEmail());
        verify(medewerkerRepository, times(0)).save(expected);
    }

    @Test
    void updateMedewerker_HappyFlow() {
        Medewerker expected = getUpdatedMedewerker();
        MedewerkerDto updateInfo = getUpdatedMedewerkerDto();
        Medewerker startData = getMedewerker();

        when(medewerkerRepository.existsById(TEST_ID)).thenReturn(true);
        when(medewerkerRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(startData));
        when(medewerkerMapper.updateEntity(startData, updateInfo)).thenReturn(expected);
        when(medewerkerRepository.save(expected)).thenReturn(expected);

        Medewerker actual = medewerkerService.updateMedewerker(TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(medewerkerRepository, times(1)).existsById(TEST_ID);
        verify(medewerkerRepository, times(1)).findById(TEST_ID);
        verify(medewerkerMapper, times(1)).updateEntity(startData, updateInfo);
        verify(medewerkerRepository, times(1)).save(expected);
    }

    @Test
    void updateMedewerker_NotFound_throws() {
        Medewerker expected = getUpdatedMedewerker();
        MedewerkerDto updateInfo = getUpdatedMedewerkerDto();
        Medewerker startData = getMedewerker();

        when(medewerkerRepository.existsById(TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> medewerkerService.updateMedewerker(TEST_ID, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(MEDEWERKER_NOT_FOUND);

        verify(medewerkerRepository, times(1)).existsById(TEST_ID);
        verify(medewerkerRepository, times(0)).findById(TEST_ID);
        verify(medewerkerMapper, times(0)).updateEntity(startData, updateInfo);
        verify(medewerkerRepository, times(0)).save(expected);
    }

    @Test
    void deleteMedewerker_happyFlow() {
        Medewerker expected = getMedewerker();
        when(medewerkerRepository.existsById(TEST_ID)).thenReturn(true);
        when(medewerkerRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(expected));

        Medewerker actual = medewerkerService.deleteMedewerker(TEST_ID);

        assertThat(actual).isEqualTo(expected);

        verify(medewerkerRepository, times(1)).existsById(TEST_ID);
        verify(medewerkerRepository, times(1)).findById(TEST_ID);
        verify(medewerkerRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    void deleteMedewerker_notFound_throws() {
        when(medewerkerRepository.existsById(TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> medewerkerService.deleteMedewerker(TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(MEDEWERKER_NOT_FOUND);

        verify(medewerkerRepository, times(1)).existsById(TEST_ID);
        verify(medewerkerRepository, times(0)).findById(TEST_ID);
        verify(medewerkerRepository, times(0)).deleteById(TEST_ID);
    }
}