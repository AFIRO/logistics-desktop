package be.zetta.logisticsdesktop.domain.packaging.service;


import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.mapper.PackagingMapper;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.packaging.PackagingTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackagingServiceTest {
    @Mock
    private PackagingRepository packagingRepository;
    @Mock
    private PackagingMapper packagingMapper;
    @InjectMocks
    private PackagingService packagingService;

    @Test
    void getAll_happyFlow() {
        Packaging listContents = getPackaging();
        List<Packaging> expected = List.of(listContents);
        when(packagingRepository.findAll()).thenReturn(expected);
        List<Packaging> actual = packagingService.getAll();

        assertThat(actual).containsExactly(listContents);
        verify(packagingRepository, times(1)).findAll();
    }

    @Test
    void getById_HappyFlow() {
        Packaging expected = getPackaging();
        when(packagingRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));
        Packaging actual = packagingService.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(packagingRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void getById_NotFound_throws() {
        when(packagingRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingService.getById(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);
        verify(packagingRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createPackaging_happyFlow() {
        Packaging expected = getPackaging();
        PackagingDto startData = getPackagingDto();
        when(packagingRepository.existsByPackagingName(startData.getPackagingName())).thenReturn(false);
        when(packagingMapper.toEntity(startData)).thenReturn(expected);
        when(packagingRepository.save(expected)).thenReturn(expected);

        Packaging actual = packagingService.createPackaging(startData);
        assertThat(actual).isEqualTo(expected);
        verify(packagingRepository, times(1)).existsByPackagingName(startData.getPackagingName());
        verify(packagingRepository, times(1)).save(expected);
    }

    @Test
    void createPackaging_alreadyExists_throwsException() {
        Packaging expected = getPackaging();
        PackagingDto startData = getPackagingDto();
        when(packagingRepository.existsByPackagingName(startData.getPackagingName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingService.createPackaging(startData));
        assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED);
        verify(packagingRepository, times(1)).existsByPackagingName(startData.getPackagingName());
        verify(packagingRepository, times(0)).save(expected);
    }

    @Test
    void updatePackaging_HappyFlow() {
        Packaging expected = getUpdatedPackaging();
        PackagingDto updateInfo = getUpdatePackagingDto();
        Packaging startData = getPackaging();

        when(packagingRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(packagingRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(startData));
        when(packagingMapper.updateEntity(startData, updateInfo)).thenReturn(expected);
        when(packagingRepository.save(expected)).thenReturn(expected);

        Packaging actual = packagingService.updatePackaging(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(packagingRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(packagingRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(packagingMapper, times(1)).updateEntity(startData, updateInfo);
        verify(packagingRepository, times(1)).save(expected);
    }

    @Test
    void updatePackaging_NotFound_throws() {
        Packaging expected = getUpdatedPackaging();
        PackagingDto updateInfo = getUpdatePackagingDto();
        Packaging startData = getPackaging();

        when(packagingRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingService.updatePackaging(MedewerkerTestData.TEST_ID, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);

        verify(packagingRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(packagingRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(packagingMapper, times(0)).updateEntity(startData, updateInfo);
        verify(packagingRepository, times(0)).save(expected);
    }

    @Test
    void deletePackaging_happyFlow() {
        Packaging expected = getPackaging();
        when(packagingRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(packagingRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));

        Packaging actual = packagingService.deletePackaging(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);

        verify(packagingRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(packagingRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(packagingRepository, times(1)).deleteById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void deletePackaging_notFound_throws() {
        when(packagingRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingService.deletePackaging(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);

        verify(packagingRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(packagingRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(packagingRepository, times(0)).deleteById(MedewerkerTestData.TEST_ID);
    }

}