package be.zetta.logisticsdesktop.gui.admin.transport;

import be.zetta.logisticsdesktop.gui.admin._AdminMenuController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TransportOverviewController extends HBox implements Initializable {

    @FXML
    public TableView<TransportData> tblTransport;
    @FXML
    public TableColumn<TransportData, String> colName;
    @FXML
    public TableColumn<TransportData, Boolean> colActive;

    @FXML
    public Button btnAddTransport;

    @Value("classpath:/fxml/admin/TransportDetailView.fxml")
    private Resource transportDetailResource;
    @Autowired
    private TransportDetailController transportDetailController;
    @Autowired
    private TransportModel transportModel;
    @Autowired
    private _AdminMenuController adminMenuController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

            transportModel.updateTransportTable();

            colName.setCellValueFactory(cell -> cell.getValue().nameProperty());
            colActive.setCellValueFactory(cell -> cell.getValue().activeProperty());

            tblTransport.getSelectionModel().clearSelection();
            tblTransport.setItems(transportModel.getTableItems());


        tblTransport.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            int selectedIndex = tblTransport.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0){
                transportDetailController.setTransportDetail(transportModel.getTableItems().get(selectedIndex));
                adminMenuController.setPaneRight(transportDetailResource);
            }
        });
    }

    @FXML
    public void handleAddButton() {

        transportDetailController.setNewTransport();
        adminMenuController.setPaneRight(transportDetailResource);

    }
}
