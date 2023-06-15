package be.zetta.logisticsdesktop.gui.magazijnier;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class KlantenController extends VBox implements Initializable {
    @Autowired
    private _MagazijnierMenuController magazijnierMenuController;
    @Autowired
    private KlantenModel klantenModel;
    @FXML
    private ListView<String> listView;
    @Value("classpath:/fxml/magazijnier/KlantenDetail.fxml")
    private Resource klantenDetailResource;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(klantenModel.getKlantenNamen());
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int index = listView.getSelectionModel().getSelectedIndex();
                klantenModel.setCustomerDetailFromIndex(index);
                magazijnierMenuController.setPaneRight(klantenDetailResource);
            }
        });
    }
    @Value("classpath:/fxml/magazijnier/KlantenAdd.fxml")
    private Resource klantenAddResource;
    @FXML
    private void klantToevoegen(){
        magazijnierMenuController.setPaneRight(klantenAddResource);
    }
}
