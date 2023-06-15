package be.zetta.logisticsdesktop.domain.order.entity.dto;

import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class CustomerOrderDto {
    private String orderId;
    @NotNull(message = "Order date may not be blank.")
    private LocalDate orderDate;
    @NotNull(message = "Order must have a status.")
    private OrderStatus status;
    @NotNull(message = "Order must have an address.")
    private AddressDto deliveryAddress;
    @NotNull(message = "Order should always contain a list.")
    private List<OrderLineDto> orderLines;
    @NotNull(message = "Order must have a customer.")
    private CustomerDto customer;
    @NotNull(message = "Order must have a purchaser.")
    private PersonDto purchaser;
    // @NotNull(message = "Order must contain a packaging.")
    private PackagingDto packaging;
    private TransportDto transport;
    private String trackTraceCode;
}
