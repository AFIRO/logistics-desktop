package be.zetta.logisticsdesktop.domain.customer.mapper;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.util.mapper.AddressMapper;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.customer.CustomerTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerMapperTest {
    @Mock
    private PersonMapper personMapper;
    @Mock
    private AddressMapper addressMapper;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private CustomerMapper customerMapper;

    @Test
    void toDto() {
        CustomerDto expected = getCustomerDto();
        Customer startData = getCustomer();
        when(addressMapper.toDto(startData.getAddress())).thenReturn(expected.getAddress());
        when(personMapper.toDto(startData.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));

        CustomerDto actual = customerMapper.toDto(startData);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getCustomerId()).isEqualTo(expected.getCustomerId());
        assertThat(actual.getLogo()).isEqualTo(expected.getLogo());
        Assertions.assertThat(actual.getBuyers()).contains(expected.getBuyers().get(0));
    }

    @Test
    void toEntity() {
        Customer expected = getCustomer();
        CustomerDto startData = getCustomerDto();

        when(addressMapper.toEntity(startData.getAddress())).thenReturn(expected.getAddress());
        when(orderMapper.toEntity(startData.getOrders().get(0))).thenReturn(expected.getOrders().get(0));
        when(personMapper.toEntity(startData.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));
        Customer actual = customerMapper.toEntity(startData);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getCustomerId()).isEqualTo(expected.getCustomerId());
        assertThat(actual.getLogo()).isEqualTo(expected.getLogo());
        Assertions.assertThat(actual.getOrders()).containsExactly(expected.getOrders().get(0));
        Assertions.assertThat(actual.getBuyers()).contains(expected.getBuyers().get(0));
    }

    @Test
    void updateEntity_Name_happyFlow() {
        Customer expected = getUpdatedNameCustomer();
        CustomerDto updateInfo = getCustomerNameUpdatedDto();
        Customer startData = getCustomer();

        when(addressMapper.updateEntity(startData.getAddress(), updateInfo.getAddress())).thenReturn(expected.getAddress());
        when(orderMapper.updateEntity(startData.getOrders().get(0), updateInfo.getOrders().get(0))).thenReturn(expected.getOrders().get(0));
        when(personMapper.updateEntity(startData.getBuyers().get(0), updateInfo.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));
        Customer actual = customerMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getCustomerId()).isEqualTo(expected.getCustomerId());
        assertThat(actual.getLogo()).isEqualTo(expected.getLogo());
        Assertions.assertThat(actual.getOrders()).containsExactly(expected.getOrders().get(0));
        Assertions.assertThat(actual.getBuyers()).containsExactly(expected.getBuyers().get(0));
    }

    @Test
    void updateEntity_UpdatedBuyerAndNewBuyer_happyFlow() {
        Customer expected = getCustomerBuyersUpdated();
        CustomerDto updateInfo = getCustomerDtoBuyersUpdated();
        Customer startData = getCustomer();

        when(addressMapper.updateEntity(startData.getAddress(), updateInfo.getAddress())).thenReturn(expected.getAddress());
        when(orderMapper.updateEntity(startData.getOrders().get(0), updateInfo.getOrders().get(0))).thenReturn(expected.getOrders().get(0));
        when(personMapper.updateEntity(startData.getBuyers().get(0), updateInfo.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));
        when(personMapper.toEntity(updateInfo.getBuyers().get(1))).thenReturn(expected.getBuyers().get(1));
        Customer actual = customerMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getCustomerId()).isEqualTo(expected.getCustomerId());
        assertThat(actual.getLogo()).isEqualTo(expected.getLogo());
        Assertions.assertThat(actual.getOrders()).containsExactly(expected.getOrders().get(0));
        Assertions.assertThat(actual.getBuyers()).containsExactly(expected.getBuyers().get(0), expected.getBuyers().get(1));
    }

    @Test
    void updateEntity_UpdatedBuyerAndUnknownBuyer_Throws() {
        Customer expected = getCustomerBuyersUpdated();
        CustomerDto updateInfo = getCustomerDtoUnknownBuyer();
        Customer startData = getCustomer();

        when(addressMapper.updateEntity(startData.getAddress(), updateInfo.getAddress())).thenReturn(expected.getAddress());
        when(personMapper.updateEntity(startData.getBuyers().get(0), updateInfo.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerMapper.updateEntity(startData, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(UNKNOWN_BUYER_IN_DTO);
    }

    @Test
    void updateEntity_UpdatedOrderAndNewOrder_happyFlow() {
        Customer expected = getCustomerOrderUpdated();
        CustomerDto updateInfo = getCustomerDtoOrderUpdated();
        Customer startData = getCustomer();

        when(addressMapper.updateEntity(startData.getAddress(), updateInfo.getAddress())).thenReturn(expected.getAddress());
        when(orderMapper.updateEntity(startData.getOrders().get(0), updateInfo.getOrders().get(0))).thenReturn(expected.getOrders().get(0));
        when(personMapper.updateEntity(startData.getBuyers().get(0), updateInfo.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));
        when(orderMapper.toEntity(updateInfo.getOrders().get(1)))
                .thenReturn(expected.getOrders().get(1));
        Customer actual = customerMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getName()).isEqualTo(expected.getName());
        Assertions.assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getCustomerId()).isEqualTo(expected.getCustomerId());
        assertThat(actual.getLogo()).isEqualTo(expected.getLogo());
        Assertions.assertThat(actual.getBuyers()).containsExactly(expected.getBuyers().get(0));
        Assertions.assertThat(actual.getOrders()).containsExactly(expected.getOrders().get(0), expected.getOrders().get(1));
    }

    @Test
    void updateEntity_UpdatedOrderAndUnknownOrder_throws() {
        Customer expected = getCustomerOrderUpdated();
        CustomerDto updateInfo = getCustomerDtoUnknownOrder();
        Customer startData = getCustomer();

        when(addressMapper.updateEntity(startData.getAddress(), updateInfo.getAddress())).thenReturn(expected.getAddress());
        when(orderMapper.updateEntity(startData.getOrders().get(0), updateInfo.getOrders().get(0))).thenReturn(expected.getOrders().get(0));
        when(personMapper.updateEntity(startData.getBuyers().get(0), updateInfo.getBuyers().get(0))).thenReturn(expected.getBuyers().get(0));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerMapper.updateEntity(startData, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(UNKNOWN_ORDER_IN_DTO);


    }
}