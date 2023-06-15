package be.zetta.logisticsdesktop.domain.order.controller;

import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.TrackAndTraceCodeDto;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log4j2
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final Validator validator;

    public List<CustomerOrderDto> getAllOrders() {
        log.info("Get All Orders called from Controller.");
        return orderService.getAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerOrderDto getById(String id) {
        log.info("Get By Id for id {} Order called from Controller.", id);
        return orderMapper.toDto(orderService.getById(id));
    }

    public List<CustomerOrderDto> getByCustomerName(String name) {
        log.info("Get By customer name {} Order called from Controller.", name);
        return orderService.findCustomerOrdersByCustomerNameContaining(name).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CustomerOrderDto> getByOrderStatus(OrderStatus status) {
        log.info("Get By status {} Order called from Controller.", status);
        return orderService.getByOrderStatus(status).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public CustomerOrderDto createOrder(CustomerOrderDto data) {
        validateDto(data);
        log.info("Create Order called from Controller.");
        return orderMapper.toDto(orderService.createOrder(data));
    }

    public CustomerOrderDto updateOrder(String id, CustomerOrderDto data) {
        validateDto(data);
        log.info("Update for id {} Order called from Controller.", id);
        return orderMapper.toDto(orderService.updateOrder(id, data));
    }

    public CustomerOrderDto deleteOrder(String id) {
        log.info("Delete for id {} Order called from Controller.", id);
        return orderMapper.toDto(orderService.deleteOrder(id));
    }

    public CustomerOrderDto processOrder(String orderId, String transportId){
        log.info("Process Order with id {} Order called from Controller.", orderId);
        return orderMapper.toDto(orderService.processOrder(orderId, transportId));
    }

    public Optional<CustomerOrderDto> getOrderBasedOnTrackAndTrace(TrackAndTraceCodeDto data){
        log.info("Get Order By T&T for code {} and extra code {} called from Controller", data.getTrackAndTraceCode(), data.getExtraCode());
        return orderService.getOrderBasedOnTrackAndTrace(data);
    }

    public List<CustomerOrderDto> getOrderBasedPurchaserId(String personId){
        log.info("Get By Purchaser Id {} called from Controller", personId);
        return orderService.getByPurchaserId(personId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    private void validateDto(CustomerOrderDto data) {
        Set<ConstraintViolation<CustomerOrderDto>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            String errormessage = violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .findFirst()
                    .get();
            log.error(errormessage);
            throw new IllegalArgumentException(errormessage);
        }
    }
}
