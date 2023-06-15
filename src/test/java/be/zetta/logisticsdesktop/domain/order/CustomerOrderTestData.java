package be.zetta.logisticsdesktop.domain.order;

import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.OrderLine;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.OrderLineDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.TrackAndTraceCodeDto;
import be.zetta.logisticsdesktop.domain.customer.CustomerTestData;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import be.zetta.logisticsdesktop.domain.packaging.PackagingTestData;
import be.zetta.logisticsdesktop.domain.product.ProductTestData;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CustomerOrderTestData {
    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_ID_NEW = "TEST_ID_NEW";
    public static final LocalDate TEST_DATE = LocalDate.now();
    public static final OrderStatus TEST_STATUS = OrderStatus.OPEN;
    public static final OrderStatus TEST_STATUS_UPDATED = OrderStatus.SENT;
    public static final String ORDER_NOT_FOUND = "Order not found";
    public static final String TEST_LINE_ID_1 = "TEST_LINE_ID_1";
    public static final String TEST_LINE_ID_2 = "TEST_LINE_ID_2";
    public static final String TEST_LINE_ID_3 = "TEST_LINE_ID_3";
    public static final double PRICE_ORDER_LINE = 15.5;
    public static final Integer QUANTITY_ORDERED_ORDER_LINE = 9;
    public static final double PRICE_ORDER_LINE_UPDATED = 100.3;
    private static final Integer QUANTITY_ORDERED_ORDER_LINE_UPDATED = 2;
    public static final String TEST_TNT_CODE = "TEST_TNT_CODE";
    public static final String TEST_EXTRA_CODE = "TEST_EXTRA_CODE";

    public static CustomerOrder getOrder() {
        return CustomerOrder.builder()
                .orderId(TEST_ID)
                .orderDate(TEST_DATE)
                .status(TEST_STATUS)
                .deliveryAddress(MedewerkerTestData.getAddress())
                .packaging(PackagingTestData.getPackaging())
                .trackTraceCode(TEST_TNT_CODE)
                .extraValidationCode(TEST_EXTRA_CODE)
                .build();
    }

    public static CustomerOrder getOrderWithOrderLines() {
        return CustomerOrder.builder()
                .orderId(TEST_ID)
                .orderDate(TEST_DATE)
                .status(TEST_STATUS)
                .orderLines(getListOrderLine())
                .packaging(PackagingTestData.getPackaging())
                .build();
    }

    public static CustomerOrder getOrderUpdated() {
        return CustomerOrder.builder()
                .orderId(TEST_ID)
                .orderDate(TEST_DATE)
                .status(TEST_STATUS_UPDATED)
                .customer(CustomerTestData.getCustomer())
                .orderLines(getUpdatedListOrderline())
                .packaging(PackagingTestData.getPackaging())
                .build();
    }

    public static CustomerOrder getNewOrder() {
        return CustomerOrder.builder()
                .orderDate(TEST_DATE)
                .status(TEST_STATUS_UPDATED)
                .packaging(PackagingTestData.getPackaging())
                .build();
    }

    public static CustomerOrderDto getOrderDto() {
        return CustomerOrderDto.builder()
                .orderId(TEST_ID)
                .orderDate(TEST_DATE)
                .status(TEST_STATUS)
                .packaging(PackagingTestData.getPackagingDto())
                .build();
    }

    public static CustomerOrderDto getOrderWithOrderLinesDto() {
        return CustomerOrderDto.builder()
                .orderId(TEST_ID)
                .orderDate(TEST_DATE)
                .status(TEST_STATUS)
                .orderLines(getListOrderlineDto())
                .packaging(PackagingTestData.getPackagingDto())
                .build();
    }

    public static CustomerOrderDto getUpdatedOrderDto() {
        return CustomerOrderDto.builder()
                .orderDate(TEST_DATE)
                .status(TEST_STATUS_UPDATED)
                .customer(CustomerTestData.getCustomerDto())
                .orderLines(getUpdatedListOrderlineDto())
                .packaging(PackagingTestData.getPackagingDto())
                .build();
    }

    public static CustomerOrderDto getUnknownOrderDto() {
        return CustomerOrderDto.builder()
                .orderId(TEST_ID_NEW)
                .orderDate(TEST_DATE)
                .status(TEST_STATUS_UPDATED)
                .packaging(PackagingTestData.getPackagingDto())
                .build();
    }

    public static List<OrderLine> getListOrderLine() {
        return Arrays.asList(
                OrderLine.builder()
                        .lineId(TEST_LINE_ID_1)
                        .unitPriceOrderLine(PRICE_ORDER_LINE)
                        .product(ProductTestData.getProduct())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE)
                        .build(),
                OrderLine.builder()
                        .lineId(TEST_LINE_ID_2)
                        .unitPriceOrderLine(PRICE_ORDER_LINE)
                        .product(ProductTestData.getProduct())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE)
                        .build(),
                OrderLine.builder()
                        .lineId(TEST_LINE_ID_3)
                        .unitPriceOrderLine(PRICE_ORDER_LINE)
                        .product(ProductTestData.getProduct())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE)
                        .build());
    }

    public static List<OrderLine> getUpdatedListOrderline() {
        return Arrays.asList(
                OrderLine.builder()
                        .lineId(TEST_LINE_ID_1)
                        .unitPriceOrderLine(PRICE_ORDER_LINE_UPDATED)
                        .product(ProductTestData.getProduct())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE_UPDATED)
                        .build(),
                OrderLine.builder()
                        .lineId(TEST_LINE_ID_2)
                        .unitPriceOrderLine(PRICE_ORDER_LINE_UPDATED)
                        .product(ProductTestData.getProduct())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE_UPDATED)
                        .build(),
                OrderLine.builder()
                        .lineId(TEST_LINE_ID_3)
                        .unitPriceOrderLine(PRICE_ORDER_LINE_UPDATED)
                        .product(ProductTestData.getProduct())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE_UPDATED)
                        .build());
    }

    public static List<OrderLineDto> getListOrderlineDto() {
        return Arrays.asList(
                OrderLineDto.builder()
                        .lineId(TEST_LINE_ID_1)
                        .unitPriceOrderLine(PRICE_ORDER_LINE)
                        .product(ProductTestData.getProductDto())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE)
                        .build(),
                OrderLineDto.builder()
                        .lineId(TEST_LINE_ID_2)
                        .unitPriceOrderLine(PRICE_ORDER_LINE)
                        .product(ProductTestData.getProductDto())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE)
                        .build(),
                OrderLineDto.builder()
                        .lineId(TEST_LINE_ID_3)
                        .unitPriceOrderLine(PRICE_ORDER_LINE)
                        .product(ProductTestData.getProductDto())
                        .build());
    }

    public static List<OrderLineDto> getUpdatedListOrderlineDto() {
        return Arrays.asList(
                OrderLineDto.builder()
                        .lineId(TEST_LINE_ID_1)
                        .unitPriceOrderLine(PRICE_ORDER_LINE_UPDATED)
                        .product(ProductTestData.getProductDto())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE_UPDATED)
                        .build(),
                OrderLineDto.builder()
                        .lineId(TEST_LINE_ID_2)
                        .unitPriceOrderLine(PRICE_ORDER_LINE_UPDATED)
                        .product(ProductTestData.getProductDto())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE_UPDATED)
                        .build(),
                OrderLineDto.builder()
                        .lineId(TEST_LINE_ID_3)
                        .unitPriceOrderLine(PRICE_ORDER_LINE_UPDATED)
                        .product(ProductTestData.getProductDto())
                        .quantityOrdered(QUANTITY_ORDERED_ORDER_LINE_UPDATED)
                        .build());
    }

    public static TrackAndTraceCodeDto getTrackAndTraceCodeDto(){
        return TrackAndTraceCodeDto.builder()
                .trackAndTraceCode(TEST_TNT_CODE)
                .extraCode(TEST_EXTRA_CODE)
                .build();
    }
}
