package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.order.controller.OrderController;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class BestellingenController extends HBox implements Initializable {


    @FXML
    private TextField txtSearchCustomerName;
    @FXML
    private ComboBox<OrderStatus> cboSearchOrderStatus;
    @FXML
    private TableView<BestellingenModel> tblCustomerOrders;
    @FXML
    private TableColumn<BestellingenModel, String> colOrderId;
    @FXML
    private TableColumn<BestellingenModel, String> colCustomerName;
    @FXML
    private TableColumn<BestellingenModel, String> colOrderDate;
    @FXML
    private TableColumn<BestellingenModel, String> colOrderStatus;

    private final ObservableList<BestellingenModel> customerOrders = FXCollections.observableArrayList();

    @Autowired
    private _MagazijnierMenuController magazijnierMenuController;
    @Value("classpath:/fxml/magazijnier/BestellingenDetailView.fxml")
    private Resource orderDetailResource;
    @Autowired
    private OrderController orderController;
    @Autowired
    private BestellingenDetailController bestellingenDetailController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBoxValues();
        setCustomerOrderTableContent();
    }

    private void setComboBoxValues() {
        cboSearchOrderStatus.getItems().setAll(OrderStatus.values());
    }

    public void setCustomerOrderTableContent() {

        colOrderId.setCellValueFactory(cellValue -> cellValue.getValue().getOrderIdProperty());
        colCustomerName.setCellValueFactory(cellValue -> cellValue.getValue().getCustomerNameProperty());
        colOrderDate.setCellValueFactory(cellValue -> cellValue.getValue().getOrderDateProperty());
        colOrderStatus.setCellValueFactory(cellValue -> cellValue.getValue().getOrderStatusProperty());

        tblCustomerOrders.setItems(getCustomerOrders());
        tblCustomerOrders.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null){
                        magazijnierMenuController.setPaneRight(orderDetailResource);
                        bestellingenDetailController.setSelectedOrderDetail(newValue);
                    }

                });
    }

    @FXML
    private void handleActionFetchOrdersByStatus(ActionEvent event) {
        tblCustomerOrders.setItems(FXCollections.observableArrayList(orderController
                .getByOrderStatus(cboSearchOrderStatus.getValue())
                .stream().map(BestellingenModel::new)
                .collect(Collectors.toList())));
        bestellingenDetailController.clearFields();
    }

    @FXML
    private void handleFetchCustomerOrdersByName() {
        tblCustomerOrders.setItems(FXCollections.observableArrayList(orderController
                .getByCustomerName(txtSearchCustomerName.getText())
                .stream().map(BestellingenModel::new)
                .collect(Collectors.toList())));
        bestellingenDetailController.clearFields();
    }

    private ObservableList<BestellingenModel> getCustomerOrders(){
        if (customerOrders.isEmpty())
            customerOrders.addAll(orderController.getAllOrders()
                    .stream().map(BestellingenModel::new)
                    .toList());
        return customerOrders;
    }

}