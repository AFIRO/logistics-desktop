package be.zetta.logisticsdesktop.domain.order.mapper;

import be.zetta.logisticsdesktop.domain.customer.repository.CustomerRepository;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.packaging.mapper.PackagingMapper;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.order.CustomerOrderTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerOrderMapperTest {

    @Mock
    private OrderLineMapper orderLineMapper;
    @Mock
    private PackagingMapper packagingMapper;
    @Mock
    private PackagingRepository packagingRepository;
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    void toDto() {
        CustomerOrderDto expectedDto = getOrderWithOrderLinesDto();
        CustomerOrder startData = getOrderWithOrderLines();

        when(orderLineMapper.toDto(startData.getOrderLines().get(0)))
                .thenReturn(expectedDto.getOrderLines().get(0));
        when(orderLineMapper.toDto(startData.getOrderLines().get(1)))
                .thenReturn(expectedDto.getOrderLines().get(1));
        when(orderLineMapper.toDto(startData.getOrderLines().get(2)))
                .thenReturn(expectedDto.getOrderLines().get(2));
        when(packagingMapper.toDto(startData.getPackaging())).thenReturn(expectedDto.getPackaging());

        CustomerOrderDto resultDto = orderMapper.toDto(startData);

        assertThat(resultDto.getOrderDate()).isEqualTo(expectedDto.getOrderDate());
        Assertions.assertThat(resultDto.getStatus()).isEqualTo(expectedDto.getStatus());
        assertThat(resultDto.getOrderLines()).isEqualTo(expectedDto.getOrderLines());
        assertThat(resultDto.getTransport()).isEqualTo(expectedDto.getTransport());
        Assertions.assertThat(resultDto.getPackaging()).isEqualTo(expectedDto.getPackaging());
        assertThat(resultDto.getTrackTraceCode()).isEqualTo(expectedDto.getTrackTraceCode());
    }

    @Test
    void toEntity() {
        CustomerOrder expected = getOrderWithOrderLines();
        CustomerOrderDto startData = getOrderWithOrderLinesDto();

        when(orderLineMapper.toEntity(startData.getOrderLines().get(0)))
                .thenReturn(expected.getOrderLines().get(0));
        when(orderLineMapper.toEntity(startData.getOrderLines().get(1)))
                .thenReturn(expected.getOrderLines().get(1));
        when(orderLineMapper.toEntity(startData.getOrderLines().get(2)))
                .thenReturn(expected.getOrderLines().get(2));
        when(packagingMapper.toEntity(startData.getPackaging())).thenReturn(expected.getPackaging());

        CustomerOrder result = orderMapper.toEntity(getOrderWithOrderLinesDto());

        assertThat(result.getOrderDate()).isEqualTo(expected.getOrderDate());
        assertThat(result.getStatus()).isEqualTo(expected.getStatus());
        assertThat(result.getOrderLines()).isEqualTo(expected.getOrderLines());
    }

    @Test
    void updateEntity() {

        /* NOTE: Set the variables for testing */
        CustomerOrder expected = getOrderUpdated();
        CustomerOrder toUpdate = getOrderWithOrderLines();
        CustomerOrderDto updateInfo = getUpdatedOrderDto();

        /* NOTE: Mock the OrderLineMapper before testing the OrderMapper */
        when(orderLineMapper.updateEntity(toUpdate.getOrderLines().get(0)
                , updateInfo.getOrderLines().get(0)))
                .thenReturn(expected.getOrderLines().get(0));
        when(orderLineMapper.updateEntity(toUpdate.getOrderLines().get(1)
                , updateInfo.getOrderLines().get(1)))
                .thenReturn(expected.getOrderLines().get(1));
        when(orderLineMapper.updateEntity(toUpdate.getOrderLines().get(2)
                , updateInfo.getOrderLines().get(2)))
                .thenReturn(expected.getOrderLines().get(2));
        when(packagingRepository.getByPackagingName(updateInfo.getPackaging().getPackagingName())).thenReturn(Optional.ofNullable(expected.getPackaging()));
        when(customerRepository.getCustomersByCustomerId(updateInfo.getCustomer().getCustomerId())).thenReturn(Optional.of(expected.getCustomer()));

        /* NOTE: Perform the test on OrderMapper.updateEntity */
        CustomerOrder result = orderMapper.updateEntity(toUpdate, updateInfo);

        /* NOTE: Check the result on different properties of the updated CustomerOrder */
        assertThat(result.getOrderDate()).isEqualTo(expected.getOrderDate());
        assertThat(result.getStatus()).isEqualTo(expected.getStatus());
        assertThat(result.getOrderLines()).isEqualTo(expected.getOrderLines());

    }


}