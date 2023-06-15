package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.order.controller.OrderController;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.order.entity.dto.OrderLineDto;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.transport.controller.TransportController;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class BestellingenDetailController extends VBox implements Initializable {

    private final String ORDER_PROCESSED = "Order is verwerkt.";
    private final String ORDER_UPDATED = "Order is aangepast.";
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnEditOrder;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private Button btnCancelUpdate;
    @FXML
    private Button btnProcessOrder;
    @FXML
    private TextField txtOrderStatus;
    @FXML
    private TextField txtOrderId;
    @FXML
    private TextField txtOrderDate;
    @FXML
    private TextField txtTrackAndTrace;
    @FXML
    private ComboBox<TransportDto> cboTransportService;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtPurchaserName;
    @FXML
    private TextField  txtPurchaserEmail;
    @FXML
    private TextField txtTotalSum;
    @FXML
    private TextArea txtaDeliveryAddress;
    @FXML
    private ListView<PackagingDto> lvPackaging;
    @FXML
    private TableView<OrderLineDto> tblOrderLines;
    @FXML
    private TableColumn<OrderLineDto, String> colProductName;
    @FXML
    private  TableColumn<OrderLineDto, String> colOrderLineQuantity;
    @FXML
    private TableColumn<OrderLineDto, String> colUnitPrice;
    @FXML
    private TableColumn<OrderLineDto, String> colOrderLineTotalPrice;
    @Autowired
    private TransportController transportController;
    @Autowired
    private OrderController orderController;

    private BestellingenModel bestellingenModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setSelectedOrderDetail(BestellingenModel order) {
        bestellingenModel = order ;
        clearFields();
        if(bestellingenModel != null){

            setOrderDetailDataEditable(false);
            cboTransportService.valueProperty().bindBidirectional(bestellingenModel.getTransportDtoObjectProperty());
            cboTransportService.getItems().addAll(transportController.getAllTransports()
                    .stream().filter(t -> t.isActive() == true).collect(Collectors.toList()));
            cboTransportService.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(TransportDto item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getTransportName());
                }
            });

            cboTransportService.setButtonCell(new ListCell<TransportDto>() {
                @Override
                protected void updateItem(TransportDto item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getTransportName());
                }
            });

            txtTrackAndTrace.textProperty().bindBidirectional(bestellingenModel.getTrackAndTraceProperty());
            txtOrderStatus.textProperty().bindBidirectional(bestellingenModel.getOrderStatusProperty());

            // Set as read only fields in the View
            txtOrderDate.textProperty().bindBidirectional(bestellingenModel.getOrderDateProperty());
            txtOrderId.textProperty().bindBidirectional(bestellingenModel.getOrderIdProperty());
            txtCustomerName.textProperty().bindBidirectional(bestellingenModel.getCustomerNameProperty());
            txtPurchaserName.textProperty().bindBidirectional(bestellingenModel.getPurchaserNameProperty());
            txtPurchaserEmail.textProperty().bindBidirectional(bestellingenModel.getPurchaserEmailProperty());
            txtTotalSum.textProperty().bindBidirectional(bestellingenModel.getTotalSumProperty());
            txtaDeliveryAddress.textProperty().bindBidirectional(bestellingenModel.getDeliveryAddressProperty());

            lvPackaging.setItems(FXCollections.observableArrayList(bestellingenModel.getPackagingListProperty()));
            lvPackaging.setCellFactory(param -> new ListCell<PackagingDto>() {
                @Override
                protected void updateItem(PackagingDto item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getPackagingName());
                }
            });
            colProductName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProduct().getName()));
            colOrderLineQuantity.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getQuantityOrdered().toString()));
            colUnitPrice.setCellValueFactory(p -> new SimpleStringProperty(Double.toString(p.getValue().getUnitPriceOrderLine())));
            colOrderLineTotalPrice.setCellValueFactory(p -> new SimpleStringProperty(Double.toString(
                    p.getValue().getUnitPriceOrderLine() * p.getValue().getQuantityOrdered())));

            tblOrderLines.setItems(FXCollections.observableArrayList(bestellingenModel.orderLinesProperty()));

            btnEditOrder.setDisable(bestellingenModel.getOrderStatusProperty().get() != OrderStatus.SENT.name());
            btnProcessOrder.setDisable(bestellingenModel.getOrderStatusProperty().get() != OrderStatus.OPEN.name());

        }
    }

    @FXML
    public void handleActionSaveChanges(ActionEvent actionEvent) {

        if (bestellingenModel != null){

            CustomerOrderDto dto = orderController.processOrder(bestellingenModel.getOrderIdProperty().get()
                    , bestellingenModel.getTransportDtoObjectProperty().get().getTransportId());

            if(!Objects.equals(bestellingenModel.getOrderStatusProperty().get(), OrderStatus.OPEN.name())){
                lblMessage.setText(ORDER_UPDATED);

            }
            else{
                lblMessage.setText(ORDER_PROCESSED);
            }

            bestellingenModel.updateCustomerOrderModel(dto);

            setOrderDetailDataEditable(false);

        }
    }

    @FXML
    private void handleActionCancelUpdate(ActionEvent event) {
        bestellingenModel.revertChanges();
        setOrderDetailDataEditable(false);
    }

    @FXML
    private void handleActionEditOrder(ActionEvent event) {
        setOrderDetailDataEditable(bestellingenModel.getOrderStatusProperty().get() == OrderStatus.SENT.name());
    }

    @FXML
    private void handleActionProcessOrder(ActionEvent event) {
        setOrderDetailDataEditable(bestellingenModel.getOrderStatusProperty().get() == OrderStatus.OPEN.name());
    }

    private void setOrderDetailDataEditable(Boolean isEditMode) {

        if(bestellingenModel != null) {
            cboTransportService.setDisable(!isEditMode);
        }

        btnProcessOrder.setDisable(isEditMode || bestellingenModel.getOrderStatusProperty().get() != OrderStatus.OPEN.name());
        btnEditOrder.setDisable(isEditMode || bestellingenModel.getOrderStatusProperty().get() != OrderStatus.SENT.name());
        btnSaveChanges.setDisable(!isEditMode);
        btnCancelUpdate.setDisable(!isEditMode);

    }

    public void clearFields(){

        lblMessage.setText("");
        lvPackaging.getItems().clear();
        tblOrderLines.getItems().clear();
        txtOrderStatus.clear();
        txtOrderDate.clear();
        txtOrderId.clear();
        txtTrackAndTrace.clear();
        cboTransportService.setValue(null);
        txtCustomerName.clear();
        txtPurchaserName.clear();
        txtPurchaserEmail.clear();
        txtTotalSum.clear();
        txtaDeliveryAddress.clear();

    }

}
