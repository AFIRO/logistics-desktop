package be.zetta.logisticsdesktop.domain.packaging.mapper;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PackagingMapper {
    public static final String ALREADY_REGISTERED = "Packaging already registered with this name.";
    private final PackagingRepository packagingRepository;

    public PackagingDto toDto(Packaging packaging) {
        return PackagingDto.builder().
                packagingId(packaging.getPackagingId())
                .packagingName(packaging.getPackagingName())
                .active(packaging.isActive())
                .height(packaging.getHeight())
                .length(packaging.getLength())
                .width(packaging.getWidth())
                .price(packaging.getPrice())
                .type(packaging.getType())
                .build();
    }

    public Packaging toEntity(PackagingDto dto) {
        return Packaging.builder().
                packagingId(dto.getPackagingId())
                .packagingName(dto.getPackagingName())
                .active(dto.isActive())
                .height(dto.getHeight())
                .length(dto.getLength())
                .width(dto.getWidth())
                .price(dto.getPrice())
                .type(dto.getType())
                .build();
    }

    public Packaging updateEntity(Packaging toUpdate, PackagingDto updateInfo) {
        updateNameIfNameNotUsed(toUpdate, updateInfo);
        toUpdate.setActive(updateInfo.isActive());
        toUpdate.setHeight(updateInfo.getHeight());
        toUpdate.setLength(updateInfo.getLength());
        toUpdate.setWidth(updateInfo.getWidth());
        toUpdate.setPrice(updateInfo.getPrice());
        toUpdate.setType(updateInfo.getType());
        return toUpdate;
    }

    private void updateNameIfNameNotUsed(Packaging toUpdate, PackagingDto updateInfo) {
        if (!toUpdate.getPackagingName().equals(updateInfo.getPackagingName())) {
            if (!packagingRepository.existsByPackagingName(updateInfo.getPackagingName())) {
                toUpdate.setPackagingName(updateInfo.getPackagingName());
            } else {
                throw new IllegalArgumentException(ALREADY_REGISTERED);
            }
        }
    }
}
