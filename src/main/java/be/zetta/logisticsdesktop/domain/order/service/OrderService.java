package be.zetta.logisticsdesktop.domain.order.service;

import be.zetta.logisticsdesktop.domain.notification.service.NotificationService;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.TrackAndTraceCodeDto;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.order.repository.OrderRepository;
import be.zetta.logisticsdesktop.domain.transport.service.TransportService;
import be.zetta.logisticsdesktop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderService {
    public static final String NOT_FOUND = "Order not found";
    private final OrderRepository orderRepository;
    private final TransportService transportService;
    private final OrderMapper orderMapper;
    private final NotificationService notificationService;
    private final UserService userService;

    public List<CustomerOrder> getAll() {
        log.info("Get All Orders called from Service.");
        return orderRepository.findAll();
    }

    public CustomerOrder getById(String id) {
        log.info("Get By Id for id {} Order called from Service.", id);
        return orderRepository.findById(id).orElseThrow(() -> {
            log.error(NOT_FOUND);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public List<CustomerOrder> getByPurchaserId(String id) {
        log.info("Get By Purchaser Id for id {} Order called from Service.", id);
        List<CustomerOrder> potentialOrders = orderRepository.findCustomerOrdersByPurchaser_PersonId(id);
        if (!potentialOrders.isEmpty()) {
            return potentialOrders;
        } else {
            log.error(NOT_FOUND);
            throw  new IllegalArgumentException(NOT_FOUND);
        }
    }


    public List<CustomerOrder> findCustomerOrdersByCustomerNameContaining(String name) {
        log.info("Get By customer name for name {} Order called from Service.", name);
        return orderRepository.findCustomerOrdersByCustomerNameIgnoreCaseContaining(name).orElseThrow(() -> {
            log.error(NOT_FOUND);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public List<CustomerOrder> getByOrderStatus(OrderStatus status) {
        log.info("Get By customer name for name {} Order called from Service.", status);
        return orderRepository.findCustomerOrderByStatus(status).orElseThrow(() -> {
            log.error(NOT_FOUND);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public CustomerOrder createOrder(CustomerOrderDto dto) {
        log.info("Create Order called from Service.");
        CustomerOrder newOrder = orderMapper.toEntity(dto);
        newOrder.setStatus(OrderStatus.OPEN);
        userService.AddNotificationsToMagazijniersForNewOrder(newOrder.getOrderId());
        return orderRepository.save(newOrder);
    }

    public CustomerOrder updateOrder(String id, CustomerOrderDto dto) {
        log.info("Update for id {} Order called from Service.", id);
        if (orderRepository.existsById(id)) {
            CustomerOrder toUpdate = orderRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            return orderRepository.save(orderMapper.updateEntity(toUpdate, dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    public CustomerOrder deleteOrder(String id) {
        log.info("Delete for id {} Order called from Service.", id);
        if (orderRepository.existsById(id)) {
            CustomerOrder deletedCustomerOrder = orderRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            orderRepository.deleteById(id);
            return deletedCustomerOrder;
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    public CustomerOrder processOrder(String orderId, String transportId) {
        CustomerOrder orderToProcess = getById(orderId);
        orderToProcess.setTransport(transportService.getById(transportId));
        orderToProcess.generateTrackAndTraceCodeAndExtraValidationCode();
        orderToProcess.setStatus(OrderStatus.SENT);
        notificationService.setNotificationsToSeen(orderId);
        userService.AddNotificationsToAankopersForProcessedOrder(orderId);
        return orderRepository.save(orderToProcess);
    }

    public Optional<CustomerOrderDto> getOrderBasedOnTrackAndTrace(TrackAndTraceCodeDto data) {
        CustomerOrder potentialOrder = orderRepository.findCustomerOrderByTrackTraceCode(data.getTrackAndTraceCode())
                .orElseThrow(()-> new IllegalArgumentException(NOT_FOUND));
        if (data.getExtraCode().equals(potentialOrder.getExtraValidationCode())){
            return Optional.ofNullable(orderMapper.toDto(potentialOrder));
        } else {
            return Optional.empty();
        }
    }
}
