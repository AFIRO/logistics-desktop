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
public class MedewerkersController extends VBox implements Initializable {
    @Autowired
    private MedewerkersDetailController medewerkersDetailController;
    @Autowired
    private _AdminMenuController adminMenuController;
    @Autowired
    private MedewerkerModel medewerkerModel;

    @Value("classpath:/fxml/admin/MedewerkerDetail.fxml")
    private Resource medewerkerDetailResource;

    @FXML
    private TableView<MedewerkerData> tblMedewerkers;
    @FXML
    private TableColumn<MedewerkerData, String> tblColName;
    @FXML
    private TableColumn<MedewerkerData, String> tblColPosition;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        medewerkerModel.updateMedewerkers();
         tblColName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tblColPosition.setCellValueFactory(new PropertyValueFactory<>("position"));

            tblMedewerkers.getSelectionModel().clearSelection();
            tblMedewerkers.setItems(medewerkerModel.getTableItems());

        tblMedewerkers.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
            int selectedIndex = tblMedewerkers.getSelectionModel().getSelectedIndex();
            medewerkersDetailController.setMedewerker(medewerkerModel.getMedewerker(selectedIndex));
            adminMenuController.setPaneRight(medewerkerDetailResource);
        });

    }
    @FXML
    private void addEmployee() {
        medewerkersDetailController.setMedewerker(null);
        adminMenuController.setPaneRight(medewerkerDetailResource);
    }
}




