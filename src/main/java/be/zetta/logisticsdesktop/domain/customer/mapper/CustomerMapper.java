package be.zetta.logisticsdesktop.domain.customer.mapper;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import be.zetta.logisticsdesktop.domain.util.mapper.AddressMapper;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor

public class CustomerMapper {
    private static final String UNKNOWN_ORDER_IN_DTO = "OrderId in customer update dto does not match known orders.";
    private static final String UNKNOWN_BUYER_IN_DTO = "PersonId in customer update dto does not match known buyers.";
    private final PersonMapper personMapper;

    private final OrderMapper orderMapper;
    private final AddressMapper addressMapper;

    @PostConstruct
    private void init(){
        this.orderMapper.setCustomerMapper(this);
    }

    public CustomerDto toDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .address(addressMapper.toDto(customer.getAddress()))
                .logo(customer.getLogo())
                .buyers(customer.getBuyers()
                        .stream()
                        .map(personMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                .customerId(dto.getCustomerId())
                .name(dto.getName())
                .logo(dto.getLogo())
                .phoneNumber(dto.getPhoneNumber())
                .address(addressMapper.toEntity(dto.getAddress()))
                .buyers(dto.getBuyers()
                        .stream()
                        .map(personMapper::toEntity)
                        .collect(Collectors.toList()))
                .orders(dto.getOrders()
                        .stream()
                        .map(orderMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }

    public Customer updateEntity(Customer toUpdate, CustomerDto updateInfo) {
        toUpdate.setName(updateInfo.getName());
        toUpdate.setLogo(updateInfo.getLogo());
        toUpdate.setPhoneNumber(updateInfo.getPhoneNumber());
        toUpdate.setAddress(addressMapper.updateEntity(toUpdate.getAddress(), updateInfo.getAddress()));
        toUpdate.setBuyers(updateBuyerList(toUpdate.getBuyers(), updateInfo.getBuyers()));
        toUpdate.setOrders(updateOrderList(toUpdate.getOrders(),updateInfo.getOrders()));
        return toUpdate;

    }
    private List<Person> updateBuyerList(List<Person> toUpdate, List<PersonDto> updateInfo) {
        for (PersonDto updatePersonInfo: updateInfo){
            if (updatePersonInfo.getPersonId() == null || updatePersonInfo.getPersonId().isBlank()){
                toUpdate.add(personMapper.toEntity(updatePersonInfo));
            } else {
                Person personToUpdate = toUpdate.stream().filter(person -> person.getPersonId().equals(updatePersonInfo.getPersonId())).findFirst().orElseThrow(()-> new IllegalArgumentException(UNKNOWN_BUYER_IN_DTO));
                Person updatedPerson = personMapper.updateEntity(personToUpdate,updatePersonInfo);
                toUpdate.set(toUpdate.indexOf(personToUpdate),updatedPerson);
            }
        }
        return toUpdate;
    }

    private List<CustomerOrder> updateOrderList(List<CustomerOrder> toUpdate, List<CustomerOrderDto> updateInfo) {
        for (CustomerOrderDto updateOrderInfo : updateInfo){
            if (updateOrderInfo.getOrderId() == null || updateOrderInfo.getOrderId().isBlank()){
                toUpdate.add(orderMapper.toEntity(updateOrderInfo));
            } else {
                CustomerOrder orderToUpdate = toUpdate.stream().filter(order -> order.getOrderId().equals(updateOrderInfo.getOrderId())).findFirst().orElseThrow(()-> new IllegalArgumentException(UNKNOWN_ORDER_IN_DTO));
                CustomerOrder updatedOrder = orderMapper.updateEntity(orderToUpdate,updateOrderInfo);
                toUpdate.set(toUpdate.indexOf(orderToUpdate),updatedOrder);
            }
        }
        return toUpdate;
    }

}
