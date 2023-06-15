package be.zetta.logisticsdesktop.domain.packaging;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.PackagingType;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;

public class PackagingTestData {
    public static final String TEST_ID = "TEST_ID";
    public static final PackagingType TEST_TYPE = PackagingType.STANDARD;
    public static final double TEST_PRICE = 69.69;
    public static final double TEST_HEIGHT = 69.70;
    public static final double TEST_LENGTH = 69.71;
    public static final double TEST_WIDTH = 69.72;
    public static final boolean TEST_ACTIVE = true;
    public static final String TEST_PACKAGE_NAME = "TEST_PACKAGE_NAME";
    public static final String TEST_PACKAGE_NAME_UPDATED = "TEST_PACKAGE_NAME_UPDATED";
    public static final String ALREADY_REGISTERED = "Packaging already registered with this name.";
    public static final String NOT_FOUND = "Packaging not found";

    public static Packaging getPackaging() {
        return Packaging.builder()
                .packagingId(TEST_ID)
                .packagingName(TEST_PACKAGE_NAME)
                .active(TEST_ACTIVE)
                .price(TEST_PRICE)
                .height(TEST_HEIGHT)
                .length(TEST_LENGTH)
                .width(TEST_WIDTH)
                .type(TEST_TYPE)
                .build();
    }

    public static PackagingDto getPackagingDto() {
        return PackagingDto.builder()
                .packagingId(TEST_ID)
                .packagingName(TEST_PACKAGE_NAME)
                .active(TEST_ACTIVE)
                .price(TEST_PRICE)
                .height(TEST_HEIGHT)
                .length(TEST_LENGTH)
                .width(TEST_WIDTH)
                .type(TEST_TYPE)
                .build();
    }

    public static PackagingDto getPackagingDtoNoId() {
        return PackagingDto.builder()
                .packagingName(TEST_PACKAGE_NAME)
                .active(TEST_ACTIVE)
                .price(TEST_PRICE)
                .height(TEST_HEIGHT)
                .length(TEST_LENGTH)
                .width(TEST_WIDTH)
                .type(TEST_TYPE)
                .build();
    }

    public static Packaging getUpdatedPackaging() {
        return Packaging.builder()
                .packagingId(TEST_ID)
                .packagingName(TEST_PACKAGE_NAME_UPDATED)
                .active(TEST_ACTIVE)
                .price(TEST_PRICE)
                .height(TEST_HEIGHT)
                .length(TEST_LENGTH)
                .width(TEST_WIDTH)
                .type(TEST_TYPE)
                .build();
    }


    public static PackagingDto getUpdatePackagingDto() {
        return PackagingDto.builder()
                .packagingId(TEST_ID)
                .packagingName(TEST_PACKAGE_NAME_UPDATED)
                .active(TEST_ACTIVE)
                .price(TEST_PRICE)
                .height(TEST_HEIGHT)
                .length(TEST_LENGTH)
                .width(TEST_WIDTH)
                .type(TEST_TYPE)
                .build();
    }
}
