package be.zetta.logisticsdesktop.domain.packaging.mapper;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.packaging.PackagingTestData.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackagingMapperTest {
    @Mock
    private PackagingRepository packagingRepository;
    @InjectMocks
    private PackagingMapper packagingMapper;

    @Test
    void toDto() {
        PackagingDto expected = getPackagingDto();
        PackagingDto actual = packagingMapper.toDto(getPackaging());

        assertThat(actual.getPackagingId()).isEqualTo(expected.getPackagingId());
        assertThat(actual.getHeight()).isEqualTo(expected.getHeight());
        assertThat(actual.getWidth()).isEqualTo(expected.getWidth());
        assertThat(actual.getLength()).isEqualTo(expected.getLength());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        AssertionsForClassTypes.assertThat(actual.getType()).isEqualTo(expected.getType());
        assertThat(actual.getPackagingName()).isEqualTo(expected.getPackagingName());
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void toEntity() {
        Packaging expected = getPackaging();
        Packaging actual = packagingMapper.toEntity(getPackagingDto());

        assertThat(actual.getPackagingId()).isEqualTo(expected.getPackagingId());
        assertThat(actual.getHeight()).isEqualTo(expected.getHeight());
        assertThat(actual.getWidth()).isEqualTo(expected.getWidth());
        assertThat(actual.getLength()).isEqualTo(expected.getLength());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getType()).isEqualTo(expected.getType());
        assertThat(actual.getPackagingName()).isEqualTo(expected.getPackagingName());
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void updateEntity_happyFlow() {
        Packaging expected = getUpdatedPackaging();
        PackagingDto updateInfo = getUpdatePackagingDto();
        Packaging startData = getPackaging();
        when(packagingRepository.existsByPackagingName(updateInfo.getPackagingName())).thenReturn(false);
        Packaging actual = packagingMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getPackagingId()).isEqualTo(expected.getPackagingId());
        assertThat(actual.getHeight()).isEqualTo(expected.getHeight());
        assertThat(actual.getWidth()).isEqualTo(expected.getWidth());
        assertThat(actual.getLength()).isEqualTo(expected.getLength());
        assertThat(actual.getPrice()).isEqualTo(expected.getPrice());
        assertThat(actual.getType()).isEqualTo(expected.getType());
        assertThat(actual.getPackagingName()).isEqualTo(expected.getPackagingName());
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
        verify(packagingRepository, times(1)).existsByPackagingName(updateInfo.getPackagingName());
    }

    @Test
    void updateEntity_packagingNameNotUnique_throws() {
        PackagingDto updateInfo = getUpdatePackagingDto();
        Packaging startData = getPackaging();
        when(packagingRepository.existsByPackagingName(updateInfo.getPackagingName())).thenReturn(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> packagingMapper.updateEntity(startData, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED);
        verify(packagingRepository, times(1)).existsByPackagingName(updateInfo.getPackagingName());
    }
}