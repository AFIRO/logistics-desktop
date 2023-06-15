package be.zetta.logisticsdesktop.domain.order.service;

import be.zetta.logisticsdesktop.domain.notification.service.NotificationService;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.TrackAndTraceCodeDto;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.order.repository.OrderRepository;
import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.service.TransportService;
import be.zetta.logisticsdesktop.domain.user.service.UserService;
import be.zetta.logisticsdesktop.domain.transport.TransportTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.order.CustomerOrderTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerOrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TransportService transportService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private OrderService orderService;
    @Captor
    private ArgumentCaptor<CustomerOrder> customerOrderArgumentCaptor;

    @Test
    void getAllCustomerOrder_happyFlow() {
        CustomerOrder listContents = getOrderWithOrderLines();
        List<CustomerOrder> expected = List.of(listContents);

        when(orderRepository.findAll()).thenReturn(expected);
        List<CustomerOrder> actual = orderService.getAll();

        assertThat(actual).containsExactly(listContents);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getCustomerOrderById_HappyFlow() {
        CustomerOrder expected = getOrderWithOrderLines();

        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(expected));
        CustomerOrder actual = orderService.getById(TEST_ID);

        assertThat(actual).isEqualTo(expected);
        verify(orderRepository, times(1)).findById(TEST_ID);
    }

    @Test
    void getCustomerOrderById_NotFound_throws() {
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.getById(TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(ORDER_NOT_FOUND);
        verify(orderRepository, times(1)).findById(TEST_ID);
    }

    @Test
    void createCustomerOrder_happyFlow() {
        CustomerOrder expected = getOrderWithOrderLines();
        CustomerOrderDto startData = getOrderWithOrderLinesDto();

        when(orderMapper.toEntity(startData)).thenReturn(expected);
        when(orderRepository.save(expected)).thenReturn(expected);

        CustomerOrder actual = orderService.createOrder(startData);

        assertThat(actual).isEqualTo(expected);
        verify(orderRepository, times(1)).save(expected);
        verify(userService,times(1)).AddNotificationsToMagazijniersForNewOrder(expected.getOrderId());
    }


    @Test
    void updateCustomerOrder_HappyFlow() {
        CustomerOrder expected = getOrderUpdated();
        CustomerOrderDto updateInfo = getUpdatedOrderDto();
        CustomerOrder startData = getOrderWithOrderLines();

        when(orderRepository.existsById(TEST_ID)).thenReturn(true);
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(startData));

        when(orderMapper.updateEntity(startData, updateInfo)).thenReturn(expected);
        when(orderRepository.save(expected)).thenReturn(expected);

        CustomerOrder actual = orderService.updateOrder(TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(orderRepository, times(1)).existsById(TEST_ID);
        verify(orderRepository, times(1)).findById(TEST_ID);
        verify(orderMapper, times(1)).updateEntity(startData, updateInfo);
        verify(orderRepository, times(1)).save(expected);
    }

    @Test
    void updateCustomerOrder_NotFound_throws() {
        CustomerOrder expected = getOrderUpdated();
        CustomerOrderDto updateInfo = getUpdatedOrderDto();
        CustomerOrder startData = getOrderWithOrderLines();

        when(orderRepository.existsById(TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.updateOrder(TEST_ID, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(ORDER_NOT_FOUND);

        verify(orderRepository, times(1)).existsById(TEST_ID);
        verify(orderRepository, times(0)).findById(TEST_ID);
        verify(orderMapper, times(0)).updateEntity(startData, updateInfo);
        verify(orderRepository, times(0)).save(expected);
    }

    @Test
    void deleteCustomerOrder_happyFlow() {
        CustomerOrder expected = getOrder();
        when(orderRepository.existsById(TEST_ID)).thenReturn(true);
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(expected));

        CustomerOrder actual = orderService.deleteOrder(TEST_ID);

        assertThat(actual).isEqualTo(expected);

        verify(orderRepository, times(1)).existsById(TEST_ID);
        verify(orderRepository, times(1)).findById(TEST_ID);
        verify(orderRepository, times(1)).deleteById(TEST_ID);
    }

    @Test
    void deleteCustomerOrder_notFound_throws() {
        when(orderRepository.existsById(TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.deleteOrder(TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(ORDER_NOT_FOUND);

        verify(orderRepository, times(1)).existsById(TEST_ID);
        verify(orderRepository, times(0)).findById(TEST_ID);
        verify(orderRepository, times(0)).deleteById(TEST_ID);
    }

    @Test
    void processOrder_happyFlow(){
        CustomerOrder startData = getOrder();
        Transport transportData = TransportTestData.getTransport();
        when(orderRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(startData));
        when(transportService.getById(TEST_ID)).thenReturn(transportData);
        when(orderRepository.save(customerOrderArgumentCaptor.capture())).thenReturn(startData);

        orderService.processOrder(TEST_ID,TEST_ID);

        CustomerOrder actual = customerOrderArgumentCaptor.getValue();

        verify(transportService,times(1)).getById(TEST_ID);
        verify(orderRepository,times(1)).findById(TEST_ID);
        verify(notificationService,times(1)).setNotificationsToSeen(TEST_ID);
        verify(userService,times(1)).AddNotificationsToAankopersForProcessedOrder(TEST_ID);
        assertThat(actual.getOrderDate()).isEqualTo(startData.getOrderDate());
        assertThat(actual.getOrderId()).isEqualTo(startData.getOrderId());
        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SENT);
        assertThat(actual.getTrackTraceCode()).isNotBlank();
        assertThat(actual.getExtraValidationCode()).isNotBlank();
        assertThat(actual.getTransport()).isEqualTo(transportData);
    }

    @Test
    void getOrderBasedOnTrackAndTrace_HappyFlow(){
        TrackAndTraceCodeDto startData = getTrackAndTraceCodeDto();
        CustomerOrder expected = getOrder();
        CustomerOrderDto expectedDto = getOrderDto();
        when(orderRepository.findCustomerOrderByTrackTraceCode(startData.getTrackAndTraceCode())).thenReturn(Optional.ofNullable(expected));
        when(orderMapper.toDto(expected)).thenReturn(expectedDto);
        Optional<CustomerOrderDto> actual = orderService.getOrderBasedOnTrackAndTrace(startData);
        assertThat(actual.isEmpty()).isFalse();
        verify(orderRepository,times(1)).findCustomerOrderByTrackTraceCode(startData.getTrackAndTraceCode());
    }

    @Test
    void getOrderBasedOnTrackAndTrace_wrongExtraCode_returnEmpty(){
        TrackAndTraceCodeDto startData = getTrackAndTraceCodeDto();
        startData.setExtraCode("WRONG");
        CustomerOrder expected = getOrder();
        CustomerOrderDto expectedDto = getOrderDto();
        when(orderRepository.findCustomerOrderByTrackTraceCode(startData.getTrackAndTraceCode())).thenReturn(Optional.ofNullable(expected));
        Optional<CustomerOrderDto> actual = orderService.getOrderBasedOnTrackAndTrace(startData);
        assertThat(actual.isEmpty()).isTrue();
        verify(orderRepository,times(1)).findCustomerOrderByTrackTraceCode(startData.getTrackAndTraceCode());
    }

    @Test
    void getOrderBasedOnTrackAndTrace_NotFound_throws(){
        TrackAndTraceCodeDto startData = getTrackAndTraceCodeDto();
        CustomerOrder expected = getOrder();
        CustomerOrderDto expectedDto = getOrderDto();
        when(orderRepository.findCustomerOrderByTrackTraceCode(startData.getTrackAndTraceCode())).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.getOrderBasedOnTrackAndTrace(startData));

        assertThat(exception.getMessage()).isEqualTo(ORDER_NOT_FOUND);
        verify(orderRepository, times(1)).findCustomerOrderByTrackTraceCode(startData.getTrackAndTraceCode());
    }

}