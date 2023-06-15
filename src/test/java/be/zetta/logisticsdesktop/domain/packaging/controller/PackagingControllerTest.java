package be.zetta.logisticsdesktop.domain.packaging.controller;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.service.PackagingService;
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
import static be.zetta.logisticsdesktop.domain.packaging.PackagingTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackagingControllerTest {
    @Mock
    private PackagingService packagingService;
    @Mock
    private Validator validator;
    @InjectMocks
    private PackagingController packagingController;

    @Test
    void getAllCustomer() {
        Packaging listContents = getPackaging();
        List<Packaging> expected = List.of(listContents);
        when(packagingService.getAll()).thenReturn(expected);
        List<Packaging> actual = packagingController.getAllPackaging();

        assertThat(actual).containsExactly(listContents);
        verify(packagingService, times(1)).getAll();
    }

    @Test
    void getById() {
        Packaging expected = getPackaging();
        when(packagingService.getById(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        Packaging actual = packagingController.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(packagingService, times(1)).getById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createPackaging_happyflow() {
        Packaging expected = getPackaging();
        PackagingDto startData = getPackagingDtoNoId();
        when(packagingService.createPackaging(startData)).thenReturn(expected);
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());

        Packaging actual = packagingController.createPackaging(startData);
        assertThat(actual).isEqualTo(expected);
        verify(packagingService, times(1)).createPackaging(startData);
    }

    @Test
    void createPackaging_IdNotEmpty_throws() {
        PackagingDto startData = getPackagingDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingController.createPackaging(startData));
        assertThat(exception.getMessage()).isEqualTo(DTO_ID_NOT_EMPTY);
        verify(packagingService, times(0)).createPackaging(startData);
    }

    @Test
    void updatePackaging_happyflow() {
        Packaging expected = getUpdatedPackaging();
        PackagingDto updateInfo = getUpdatePackagingDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(packagingService.updatePackaging(MedewerkerTestData.TEST_ID, updateInfo)).thenReturn(expected);
        Packaging actual = packagingController.updatePackaging(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(packagingService, times(1)).updatePackaging(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void updatePackaging_IdDoesNotMatch_throws() {
        PackagingDto updateInfo = getUpdatePackagingDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingController.updatePackaging("WRONG", updateInfo));
        assertThat(exception.getMessage()).isEqualTo(ID_MUST_MATCH_ID_IN_DTO);
        verify(packagingService, times(0)).updatePackaging(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void deletePackaging() {
        Packaging expected = getPackaging();
        when(packagingService.deletePackaging(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        Packaging actual = packagingController.deletePackaging(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);
        verify(packagingService, times(1)).deletePackaging(MedewerkerTestData.TEST_ID);
    }
}