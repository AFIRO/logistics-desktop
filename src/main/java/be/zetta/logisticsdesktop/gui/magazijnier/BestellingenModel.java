package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.OrderLineDto;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.EnumSet;


public class BestellingenModel {

    // String properties zijn bidirectioneel verbonden met de text velden in de controller
    private final StringProperty trackAndTraceProperty ;
    private final StringProperty orderStatusProperty;
    private final StringProperty orderDateProperty;
    private final StringProperty orderIdProperty;
    private final StringProperty customerNameProperty;
    private final StringProperty purchaserNameProperty;
    private final StringProperty purchaserEmailProperty;
    private final StringProperty totalSumProperty;
    private final StringProperty deliveryAddressProperty;
    private final ObjectProperty<TransportDto> transportDtoObjectProperty;
    private final ListProperty<PackagingDto> packagingListProperty;
    private final ListProperty<OrderLineDto> orderLinesProperty;
    private CustomerOrderDto orderDto;
    private CustomerOrderDto orderDtoBefore;

    public BestellingenModel(CustomerOrderDto orderDto) {

        this.orderDto = orderDto;
        this.orderDtoBefore = orderDto;

        trackAndTraceProperty = new SimpleStringProperty(orderDto.getTrackTraceCode());
        orderStatusProperty = new SimpleStringProperty(orderDto.getStatus().name());
        orderDateProperty = new SimpleStringProperty(orderDto.getOrderDate().toString());
        transportDtoObjectProperty = new SimpleObjectProperty<>(orderDto.getTransport());
        orderIdProperty = new SimpleStringProperty(orderDto.getOrderId());
        customerNameProperty = new SimpleStringProperty(orderDto.getCustomer().getName());
        purchaserNameProperty = new SimpleStringProperty(orderDto.getPurchaser().getFirstName() + " " + orderDto.getPurchaser().getLastName());
        purchaserEmailProperty = new SimpleStringProperty(orderDto.getPurchaser().getEmail());
        packagingListProperty = new SimpleListProperty<>(FXCollections.observableArrayList(orderDto.getPackaging()));
        totalSumProperty = new SimpleStringProperty(Double.toString(orderDto.getOrderLines()
                .stream()
                .mapToDouble(l -> l.getQuantityOrdered() * l.getUnitPriceOrderLine()).sum()));
        deliveryAddressProperty = new SimpleStringProperty(orderDto.getDeliveryAddress().getStreet()
                + " " + orderDto.getDeliveryAddress().getHouseNumber()
                + "\n" + orderDto.getDeliveryAddress().getCountry()
                + " " + orderDto.getDeliveryAddress().getPostalCode());
        orderLinesProperty = new SimpleListProperty<>(FXCollections.observableArrayList(orderDto.getOrderLines()));

        // Listeners are needed to change the value in the DTO when user made changes
        trackAndTraceProperty.addListener((observableValue, s, t1) -> this.orderDto.setTrackTraceCode(t1));
        orderStatusProperty.addListener((observableValue, s, t1) -> {
                   if(EnumSet.allOf(OrderStatus.class)
                    .stream()
                    .anyMatch(e -> e.name().equals(t1))){
                       this.orderDto.setStatus(OrderStatus.valueOf(t1));
                   }
        });
        transportDtoObjectProperty.addListener((observableValue, transportDto, t1) -> this.orderDto.setTransport(t1));


        purchaserEmailProperty.addListener((observableValue, s, t1) -> this.orderDto.getPurchaser().setEmail(t1));

    }

    public CustomerOrderDto getUpdatedOrder() {
        return this.orderDto;
    }

    public void revertChanges() {
        updateCustomerOrderModel(orderDtoBefore);
    }

    public void updateCustomerOrderModel(CustomerOrderDto orderDto){

        // This method is needed to update the FE when data was changed in the BE
        this.orderDto = orderDto;
        this.orderDtoBefore = orderDto;
        trackAndTraceProperty.set(orderDto.getTrackTraceCode());
        orderStatusProperty.set(orderDto.getStatus().name());
        orderDateProperty.set(orderDto.getOrderDate().toString());
        transportDtoObjectProperty.set(orderDto.getTransport());
        orderIdProperty.set(orderDto.getOrderId());
        customerNameProperty.set(orderDto.getCustomer().getName());
        purchaserNameProperty.set(orderDto.getPurchaser().getFirstName() + " " + orderDto.getPurchaser().getLastName());
        purchaserEmailProperty.set(orderDto.getPurchaser().getEmail());
        packagingListProperty.set(FXCollections.observableArrayList(orderDto.getPackaging()));
        totalSumProperty.set(Double.toString(orderDto.getOrderLines()
                .stream()
                .mapToDouble(l -> l.getQuantityOrdered() * l.getUnitPriceOrderLine()).sum()));
        deliveryAddressProperty.set(orderDto.getDeliveryAddress().getStreet()
                + " " + orderDto.getDeliveryAddress().getHouseNumber()
                + "\n" + orderDto.getDeliveryAddress().getCountry()
                + " " + orderDto.getDeliveryAddress().getPostalCode());
        orderLinesProperty.set(FXCollections.observableArrayList(orderDto.getOrderLines()));

    }

    public ListProperty<OrderLineDto> orderLinesProperty() {
        return orderLinesProperty;
    }
    public ObjectProperty<TransportDto> getTransportDtoObjectProperty() {
        return transportDtoObjectProperty;
    }
    public StringProperty getTrackAndTraceProperty() {
        return trackAndTraceProperty;
    }
    public StringProperty getOrderStatusProperty() {
        return orderStatusProperty;
    }
    public StringProperty getOrderDateProperty() {
        return orderDateProperty;
    }
    public StringProperty getOrderIdProperty() {
        return orderIdProperty;
    }
    public StringProperty getCustomerNameProperty() {
        return customerNameProperty;
    }
    public StringProperty getPurchaserNameProperty() {
        return purchaserNameProperty;
    }
    public StringProperty getPurchaserEmailProperty() {
        return purchaserEmailProperty;
    }
    public StringProperty getDeliveryAddressProperty() {
        return deliveryAddressProperty;
    }
    public StringProperty getTotalSumProperty() {
        return totalSumProperty;
    }
    public ListProperty<PackagingDto> getPackagingListProperty() {
        return packagingListProperty;
    }


}
