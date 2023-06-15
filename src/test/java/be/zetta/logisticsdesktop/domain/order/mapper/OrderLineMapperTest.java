package be.zetta.logisticsdesktop.domain.order.mapper;

import be.zetta.logisticsdesktop.domain.order.entity.OrderLine;
import be.zetta.logisticsdesktop.domain.order.entity.dto.OrderLineDto;
import be.zetta.logisticsdesktop.domain.product.mapper.ProductMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.order.CustomerOrderTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderLineMapperTest {

    @InjectMocks
    private OrderLineMapper orderLineMapper;

    @Mock
    private ProductMapper productMapper;

    @Test
    void toDto() {
        OrderLineDto expectedDto = getListOrderlineDto().get(0);
        OrderLine startData = getListOrderLine().get(0);

        when(productMapper.toDto(startData.getProduct())).thenReturn(expectedDto.getProduct());

        OrderLineDto result = orderLineMapper.toDto(startData);

        assertThat(result.getLineId()).isEqualTo(expectedDto.getLineId());
        assertThat(result.getQuantityOrdered()).isEqualTo(expectedDto.getQuantityOrdered());
        assertThat(result.getUnitPriceOrderLine()).isEqualTo(expectedDto.getUnitPriceOrderLine());
        assertThat(result.getCustomerOrder()).isEqualTo(expectedDto.getCustomerOrder());
        assertThat(result.getProduct()).isEqualTo(expectedDto.getProduct());

    }

    @Test
    void toEntity() {

        OrderLineDto startData = getListOrderlineDto().get(0);
        OrderLine expected = getListOrderLine().get(0);

        when(productMapper.toEntity(startData.getProduct())).thenReturn(expected.getProduct());

        OrderLine result = orderLineMapper.toEntity(startData);

        assertThat(result.getLineId()).isEqualTo(expected.getLineId());
        assertThat(result.getQuantityOrdered()).isEqualTo(expected.getQuantityOrdered());
        assertThat(result.getUnitPriceOrderLine()).isEqualTo(expected.getUnitPriceOrderLine());
        Assertions.assertThat(result.getProduct()).isEqualTo(expected.getProduct());

    }

    @Test
    void updateEntity() {
        OrderLine expected = getUpdatedListOrderline().get(0);
        OrderLine toUpdate = getListOrderLine().get(0);
        OrderLineDto updateInfo = getUpdatedListOrderlineDto().get(0);

        OrderLine result = orderLineMapper.updateEntity(toUpdate, updateInfo);

        assertThat(result.getQuantityOrdered()).isEqualTo(expected.getQuantityOrdered());

    }

}
