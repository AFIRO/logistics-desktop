package be.zetta.logisticsdesktop.gui.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DozenController extends VBox implements Initializable {
    @Autowired
    private DoosDetailController doosDetailController;
    @Autowired
    private _AdminMenuController adminMenuController;
    @Autowired
    private DozenModel dozenModel;
    @Value("classpath:/fxml/admin/DoosDetail.fxml")
    private Resource doosDetailResource;

    @FXML
    private TableView<DoosData> tblDozen;
    @FXML
    private TableColumn<DoosData, String> tblColName;
    @FXML
    private TableColumn<DoosData, Double> tblColPrice;
    @FXML
    private TableColumn<DoosData, Double> tblColHeight;
    @FXML
    private TableColumn<DoosData, Double> tblColWidth;
    @FXML
    private TableColumn<DoosData, Double> tblColLength;
    @FXML
    private TableColumn<DoosData, String> tblColType;
    @FXML
    private TableColumn<DoosData, Boolean> tblColActive;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dozenModel.updateDozen();
            tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tblColHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
            tblColWidth.setCellValueFactory(new PropertyValueFactory<>("width"));
            tblColLength.setCellValueFactory(new PropertyValueFactory<>("length"));
            tblColPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            tblColType.setCellValueFactory(new PropertyValueFactory<>("type"));
            tblColActive.setCellValueFactory(new PropertyValueFactory<>("active"));

            tblDozen.getSelectionModel().clearSelection();
            tblDozen.setItems(dozenModel.getTableItems());

        tblDozen.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            int selectedIndex = tblDozen.getSelectionModel().getSelectedIndex();
            doosDetailController.setDoos(dozenModel.getDoos(selectedIndex));
            adminMenuController.setPaneRight(doosDetailResource);
        });
    }
    @FXML
    private void addBox() {
        doosDetailController.setDoos(null);
        adminMenuController.setPaneRight(doosDetailResource);
    }
}
