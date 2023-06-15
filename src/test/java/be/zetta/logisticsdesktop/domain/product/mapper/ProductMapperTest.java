package be.zetta.logisticsdesktop.domain.product.mapper;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.product.ProductTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Test
    void toDto() {
        ProductDto expectedDto = getProductDto();
        Product startData = getProduct();

        ProductDto result = productMapper.toDto(startData);

        assertThat(result.getProductId()).isEqualTo(expectedDto.getProductId());
        assertThat(result.getName()).isEqualTo(expectedDto.getName());
        assertThat(result.getUnitPrice()).isEqualTo(expectedDto.getUnitPrice());

    }

    @Test
    void toEntity() {
        Product expected = getProduct();
        ProductDto startData = getProductDto();

        Product result = productMapper.toEntity(startData);

        assertThat(result.getProductId()).isEqualTo(expected.getProductId());
        assertThat(result.getName()).isEqualTo(expected.getName());
        assertThat(result.getUnitPrice()).isEqualTo(expected.getUnitPrice());

    }

    @Test
    void updateEntity() {
        Product expected = getProductUpdated();
        Product toUpdate = getProduct();
        ProductDto updateInfo = getProductDtoUpdated();

        Product result = productMapper.updateEntity(toUpdate, updateInfo);

        assertThat(result.getName()).isEqualTo(expected.getName());
        assertThat(result.getUnitPrice()).isEqualTo(expected.getUnitPrice());

    }

}
