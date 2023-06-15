package be.zetta.logisticsdesktop.domain.order.controller;

import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.TrackAndTraceCodeDto;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.order.CustomerOrderTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerOrderControllerTest {
    @Mock
    private OrderService orderService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private Validator validator;
    @InjectMocks
    private OrderController orderController;

    @Test
    void getAllCustomerOrders() {

        List<CustomerOrderDto> expectedDto = List.of(getOrderDto());
        List<CustomerOrder> expectedOrder = List.of(getOrder());

        when(orderService.getAll()).thenReturn(expectedOrder);
        when(orderMapper.toDto(expectedOrder.get(0))).thenReturn(expectedDto.get(0));

        List<CustomerOrderDto> result = orderController.getAllOrders();

        assertThat(result).containsAll(expectedDto);
        verify(orderService, times(1)).getAll();
    }

    @Test
    void getCustomerOrderById() {
        CustomerOrderDto expectedDto = getOrderDto();
        CustomerOrder expectedOrder = getOrder();

        when(orderMapper.toDto(expectedOrder)).thenReturn(expectedDto);
        when(orderService.getById(TEST_ID)).thenReturn(expectedOrder);

        CustomerOrderDto actual = orderController.getById(TEST_ID);
        assertThat(actual).isEqualTo(expectedDto);
        verify(orderService, times(1)).getById(TEST_ID);
    }

    @Test
    void createCustomerOrder() {
        CustomerOrderDto expectedDto = getOrderWithOrderLinesDto();
        CustomerOrder expectedOrder = getOrderWithOrderLines();
        CustomerOrderDto startData = getOrderWithOrderLinesDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(orderMapper.toDto(expectedOrder)).thenReturn(expectedDto);
        when(orderService.createOrder(startData)).thenReturn(expectedOrder);

        CustomerOrderDto actual = orderController.createOrder(startData);

        assertThat(actual).isEqualTo(expectedDto);
        verify(orderService, times(1)).createOrder(startData);
    }

    @Test
    void updateCustomerOrder() {
        CustomerOrderDto expectedDto = getUpdatedOrderDto();
        CustomerOrder expectedOrder = getOrderUpdated();
        CustomerOrderDto updateInfo = getUpdatedOrderDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(orderMapper.toDto(expectedOrder)).thenReturn(expectedDto);
        when(orderService.updateOrder(TEST_ID, updateInfo)).thenReturn(expectedOrder);

        CustomerOrderDto actual = orderController.updateOrder(TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expectedDto);
        verify(orderService, times(1)).updateOrder(TEST_ID, updateInfo);
    }

    @Test
    void deleteCustomerOrder() {
        CustomerOrderDto expectedDto = getOrderDto();
        CustomerOrder expected = getOrder();

        when(orderMapper.toDto(expected)).thenReturn(expectedDto);
        when(orderService.deleteOrder(TEST_ID)).thenReturn(expected);

        CustomerOrderDto actual = orderController.deleteOrder(TEST_ID);

        assertThat(actual).isEqualTo(expectedDto);
        verify(orderService, times(1)).deleteOrder(TEST_ID);
    }

    @Test
    void processCustomerOrder() {
        CustomerOrderDto expectedDto = getOrderDto();
        CustomerOrder expected = getOrder();

        when(orderMapper.toDto(expected)).thenReturn(expectedDto);
        when(orderService.processOrder(TEST_ID,TEST_ID)).thenReturn(expected);

        CustomerOrderDto actual = orderController.processOrder(TEST_ID,TEST_ID);

        assertThat(actual).isEqualTo(expectedDto);
        verify(orderService, times(1)).processOrder(TEST_ID,TEST_ID);
    }

    @Test
    void getOrderTnTCustomerOrder() {
        CustomerOrderDto expectedDto = getOrderDto();
        TrackAndTraceCodeDto startData = getTrackAndTraceCodeDto();

        when(orderService.getOrderBasedOnTrackAndTrace(startData)).thenReturn(Optional.ofNullable(expectedDto));

        Optional<CustomerOrderDto> actual = orderController.getOrderBasedOnTrackAndTrace(startData);

        assertThat(actual.get()).isEqualTo(expectedDto);
        verify(orderService, times(1)).getOrderBasedOnTrackAndTrace(startData);
    }



}