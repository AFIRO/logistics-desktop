package be.zetta.logisticsdesktop.domain.order.mapper;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.customer.mapper.CustomerMapper;
import be.zetta.logisticsdesktop.domain.customer.repository.CustomerRepository;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.OrderLine;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.OrderLineDto;
import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.mapper.PackagingMapper;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.mapper.TransportMapper;
import be.zetta.logisticsdesktop.domain.transport.repository.TransportRepository;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import be.zetta.logisticsdesktop.domain.util.mapper.AddressMapper;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import be.zetta.logisticsdesktop.domain.util.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private static final String UNKNOWN_ORDERLINE_IN_DTO = "LineId in order line update dto does not match known order lines.";
    public static final String UNKNOWN_PACKAGE = "Package Dto contained in Order Dto contains an unknown package.";
    public static final String UNKNOWN_TRANSPORT = "Transport Dto contained in Order Dto contains an unknown transport.";
    private static final String UNKNOWN_PERSON = "Person Dto contained in Order Dto contains an unknown person.";
    private static final String UNKNOWN_CUSTOMER = "Customer Dto contained in Order Dto contains an unknown customer.";
    private final OrderLineMapper orderLineMapper;
    private final PackagingMapper packagingMapper;
    private final TransportMapper transportMapper;
    private final PersonMapper personMapper;
    private final AddressMapper addressMapper;
    private final PackagingRepository packagingRepository;
    private final TransportRepository transportRepository;
    private final PersonRepository personRepository;
    private final CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    public void setCustomerMapper(CustomerMapper customerMapper){
        this.customerMapper = customerMapper;
    }

    public CustomerOrderDto toDto(CustomerOrder customerOrder) {
        return CustomerOrderDto.builder()
                .orderId(customerOrder.getOrderId())
                .status(customerOrder.getStatus())
                .orderDate(customerOrder.getOrderDate())
                .orderLines(customerOrder.getOrderLines()
                        .stream()
                        .map(orderLineMapper::toDto)
                        .collect(Collectors.toList()))
                .packaging(customerOrder.getPackaging() != null ? packagingMapper.toDto(customerOrder.getPackaging()): null)
                .transport(customerOrder.getTransport() != null ? transportMapper.toDto(customerOrder.getTransport()): null)
                .purchaser(customerOrder.getPurchaser() != null ? personMapper.toDto(customerOrder.getPurchaser()): null)
                .customer(customerOrder.getCustomer() != null ? customerMapper.toDto(customerOrder.getCustomer()): null)
                .deliveryAddress(customerOrder.getDeliveryAddress() != null ? addressMapper.toDto(customerOrder.getDeliveryAddress()): null)
                .trackTraceCode(customerOrder.getTrackTraceCode())
                .build();
    }

    public CustomerOrder toEntity(CustomerOrderDto dto) {
        return CustomerOrder.builder()
                .orderId(dto.getOrderId())
                .orderDate(dto.getOrderDate())
                .status(dto.getStatus())
                .orderLines(dto.getOrderLines()
                        .stream()
                        .map(orderLineMapper::toEntity)
                        .collect(Collectors.toList()))
                .packaging(dto.getPackaging() != null ? packagingMapper.toEntity(dto.getPackaging()): null)
                .transport(dto.getTransport() != null ? transportMapper.toEntity(dto.getTransport()): null)
                .purchaser(dto.getPurchaser() != null ? personMapper.toEntity(dto.getPurchaser()): null)
                .customer(dto.getCustomer() != null ? customerMapper.toEntity(dto.getCustomer()): null)
                .deliveryAddress(dto.getDeliveryAddress() != null ? addressMapper.toEntity(dto.getDeliveryAddress()): null)
                .trackTraceCode(dto.getTrackTraceCode())
                .build();
    }

    public CustomerOrder updateEntity(CustomerOrder toUpdate, CustomerOrderDto updateInfo) {
        toUpdate.setOrderDate(updateInfo.getOrderDate());
        toUpdate.setStatus(updateInfo.getStatus());
        toUpdate.setOrderLines(updateOrderLineList(toUpdate.getOrderLines(), updateInfo.getOrderLines()));
        toUpdate.setPackaging(updateInfo.getPackaging() != null ? updatePackaging(updateInfo.getPackaging()) : null);
        toUpdate.setCustomer(updateInfo.getCustomer() != null ? updateCustomer(updateInfo.getCustomer()) : null);
        toUpdate.setTransport(updateInfo.getTransport() != null ? updateTransport(updateInfo.getTransport()) : null);
        toUpdate.setPurchaser(updateInfo.getPurchaser() != null ? updatePerson(updateInfo.getPurchaser()) : null);
        toUpdate.setTrackTraceCode(updateInfo.getTrackTraceCode());

        return toUpdate;
    }

    private Transport updateTransport(TransportDto transport) {
        return transportMapper.updateEntity(transportMapper.toEntity(transport),transport);

    }

    private Person updatePerson(PersonDto person) {
        return personRepository.getPersonByPersonId(person.getPersonId())
                .orElseThrow(()-> new IllegalArgumentException(UNKNOWN_PERSON));
    }

    private Customer updateCustomer(CustomerDto customer) {
        return customerRepository.getCustomersByCustomerId(customer.getCustomerId())
                .orElseThrow(()-> new IllegalArgumentException(UNKNOWN_CUSTOMER));
    }

    private Packaging updatePackaging(PackagingDto packaging) {
        return packagingRepository.getByPackagingName(packaging.getPackagingName())
                .orElseThrow(()-> new IllegalArgumentException(UNKNOWN_PACKAGE));
    }

    private List<OrderLine> updateOrderLineList(List<OrderLine> toUpdate, List<OrderLineDto> updateInfo) {
        for (OrderLineDto updateOrderLineInfo : updateInfo) {
            if (updateOrderLineInfo.getLineId() == null || updateOrderLineInfo.getLineId().isBlank()) {
                toUpdate.add(orderLineMapper.toEntity(updateOrderLineInfo));
            } else {
                OrderLine orderLineToUpdate = toUpdate.stream()
                        .filter(orderLine -> orderLine.getLineId()
                                .equals(updateOrderLineInfo.getLineId()))
                        .findFirst().orElseThrow(() -> new IllegalArgumentException(UNKNOWN_ORDERLINE_IN_DTO));
                OrderLine updatedOrderLine = orderLineMapper.updateEntity(orderLineToUpdate, updateOrderLineInfo);
                toUpdate.set(toUpdate.indexOf(orderLineToUpdate), updatedOrderLine);
            }
        }
        return toUpdate;
    }
}
