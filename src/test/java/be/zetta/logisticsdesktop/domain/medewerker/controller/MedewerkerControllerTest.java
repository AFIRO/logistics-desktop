package be.zetta.logisticsdesktop.domain.medewerker.controller;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.medewerker.service.MedewerkerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;

import static be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedewerkerControllerTest {
    @Mock
    private MedewerkerService medewerkerService;
    @Mock
    private Validator validator;
    @InjectMocks
    private MedewerkerController medewerkerController;

    @Test
    void getAllMedewerkers() {
        Medewerker listContents = getMedewerker();
        List<Medewerker> expected = List.of(listContents);
        when(medewerkerService.getAll()).thenReturn(expected);
        List<Medewerker> actual = medewerkerController.getAllMedewerkers();

        assertThat(actual).containsExactly(listContents);
        verify(medewerkerService, times(1)).getAll();
    }

    @Test
    void getById() {
        Medewerker expected = getMedewerker();
        when(medewerkerService.getById(TEST_ID)).thenReturn(expected);
        Medewerker actual = medewerkerController.getById(TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(medewerkerService, times(1)).getById(TEST_ID);
    }

    @Test
    void createMedewerker() {
        Medewerker expected = getMedewerker();
        MedewerkerDto startData = getMedewerkerDto();
        when(medewerkerService.createMedewerker(startData)).thenReturn(expected);
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());

        Medewerker actual = medewerkerController.createMedewerker(startData);
        assertThat(actual).isEqualTo(expected);
        verify(medewerkerService, times(1)).createMedewerker(startData);
    }

    @Test
    void updateMedewerker() {
        Medewerker expected = getUpdatedMedewerker();
        MedewerkerDto updateInfo = getUpdatedMedewerkerDto();
        when(medewerkerService.updateMedewerker(TEST_ID, updateInfo)).thenReturn(expected);
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Medewerker actual = medewerkerController.updateMedewerker(TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(medewerkerService, times(1)).updateMedewerker(TEST_ID, updateInfo);
    }

    @Test
    void deleteMedewerker() {
        Medewerker expected = getMedewerker();
        when(medewerkerService.deleteMedewerker(TEST_ID)).thenReturn(expected);
        Medewerker actual = medewerkerController.deleteMedewerker(TEST_ID);

        assertThat(actual).isEqualTo(expected);
        verify(medewerkerService, times(1)).deleteMedewerker(TEST_ID);
    }
}