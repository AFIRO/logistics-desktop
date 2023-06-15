package be.zetta.logisticsdesktop.domain.customer;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;

import java.util.ArrayList;
import java.util.Arrays;

import static be.zetta.logisticsdesktop.domain.order.CustomerOrderTestData.*;
import static be.zetta.logisticsdesktop.domain.util.mapper.UtilTestData.*;

public class CustomerTestData {
    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_PHONENUMBER = "TEST_PHONENUMBER";
    public static final byte[] TEST_LOGO = "TEST_LOGO".getBytes();
    public static final String TEST_NAME = "TEST_NAME";
    public static final String TEST_NAME_UPDATED = "TEST_NAME_UPDATED";
    public static final String UNKNOWN_ORDER_IN_DTO = "OrderId in customer update dto does not match known orders.";
    public static final String UNKNOWN_BUYER_IN_DTO = "PersonId in customer update dto does not match known buyers.";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String CUSTOMER_ALREADY_EXISTS = "Customer already exists";
    public static final String DTO_ID_NOT_EMPTY = "Dto Id must be empty for create.";
    public static final String ID_MUST_MATCH_ID_IN_DTO = "Id must match Id in Dto.";

    public static Customer getCustomer() {
        return Customer.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddress())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPerson())))
                .orders(new ArrayList<>(Arrays.asList(getOrder())))
                .build();
    }

    public static CustomerDto getCustomerDto() {
        return CustomerDto.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddressDto())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto())))
                .build();
    }

    public static CustomerDto getCreateCustomerDto() {
        return CustomerDto.builder()
                .name(TEST_NAME)
                .address(getAddressDto())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto())))
                .build();
    }

    public static Customer getUpdatedNameCustomer() {
        return Customer.builder()
                .customerId(TEST_ID)
                .address(getAddress())
                .name(TEST_NAME_UPDATED)
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPerson())))
                .orders(new ArrayList<>(Arrays.asList(getOrder())))
                .build();
    }

    public static CustomerDto getCustomerNameUpdatedDto() {
        return CustomerDto.builder()
                .customerId(TEST_ID)
                .address(getAddressDto())
                .name(TEST_NAME_UPDATED)
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto())))
                .build();
    }

    public static Customer getCustomerBuyersUpdated() {
        return Customer.builder()
                .customerId(TEST_ID)
                .address(getAddress())
                .name(TEST_NAME)
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getUpdatedPerson(), getNewPerson())))
                .orders(new ArrayList<>(Arrays.asList(getOrder())))
                .build();
    }

    public static CustomerDto getCustomerDtoBuyersUpdated() {
        return CustomerDto.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddressDto())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getUpdatedPersonDto(), getNewPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto())))
                .build();
    }

    public static CustomerDto getCustomerDtoUnknownBuyer() {
        return CustomerDto.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddressDto())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getUpdatedPersonDto(), getUnknownPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto())))
                .build();
    }

    public static Customer getCustomerOrderUpdated() {
        return Customer.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddress())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPerson())))
                .orders(new ArrayList<>(Arrays.asList(getOrder(), getNewOrder())))
                .build();
    }

    public static CustomerDto getCustomerDtoOrderUpdated() {
        return CustomerDto.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddressDto())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto(), getUpdatedOrderDto())))
                .build();
    }

    public static CustomerDto getCustomerDtoUnknownOrder() {
        return CustomerDto.builder()
                .customerId(TEST_ID)
                .name(TEST_NAME)
                .address(getAddressDto())
                .phoneNumber(TEST_PHONENUMBER)
                .logo(TEST_LOGO)
                .buyers(new ArrayList<>(Arrays.asList(getPersonDto())))
                .orders(new ArrayList<>(Arrays.asList(getOrderDto(), getUnknownOrderDto())))
                .build();
    }


}
