package be.zetta.logisticsdesktop.domain.product;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;

public class ProductTestData {

    public static final String PRODUCT_ID = "PRODUCT_TEST_ID";
    public static final String PRODUCT_NAME = "PRODUCT_TEST_NAME";
    public static final String PRODUCT_NAME_UPDATED = "PRODUCT_TEST_NAME_UPDATED";
    public static final double PRODUCT_UNIT_PRICE = 4.5;
    public static final double PRODUCT_UNIT_PRICE_UPDATED = 6;
    public static final String NOT_FOUND = "Product not found";
    public static final String ALREADY_REGISTERED = "Product already registered with this name.";
    public static final String DTO_ID_NOT_EMPTY = "Dto Id must be empty for create.";
    public static final String ID_MUST_MATCH_ID_IN_DTO = "Id must match Id in Dto.";

    public static Product getProduct() {
        return Product.builder()
                .productId(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .unitPrice(PRODUCT_UNIT_PRICE)
                .build();
    }

    public static Product getProductUpdated() {
        return Product.builder()
                .productId(PRODUCT_ID)
                .name(PRODUCT_NAME_UPDATED)
                .unitPrice(PRODUCT_UNIT_PRICE_UPDATED)
                .build();
    }


    public static ProductDto getProductDto() {
        return ProductDto.builder()
                .productId(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .unitPrice(PRODUCT_UNIT_PRICE)
                .build();
    }

    public static ProductDto getProductDtoNoId() {
        return ProductDto.builder()
                .name(PRODUCT_NAME)
                .unitPrice(PRODUCT_UNIT_PRICE)
                .build();
    }


    public static ProductDto getProductDtoUpdated() {
        return ProductDto.builder()
                .productId(PRODUCT_ID)
                .name(PRODUCT_NAME_UPDATED)
                .unitPrice(PRODUCT_UNIT_PRICE_UPDATED)
                .build();
    }

}
