package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.user.context.UserContext;
import be.zetta.logisticsdesktop.gui.GuiBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

@Component
@Log4j2
public class _MagazijnierMenuController implements Initializable {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserContext userContext;
    @FXML
    private Button btnKlanten;
    @FXML
    private Button btnBestellingen;
    @FXML
    private BorderPane mainView;
    @Value("classpath:/fxml/Login.fxml")
    private Resource loginResource;
    @Value("classpath:/fxml/magazijnier/Klanten.fxml")
    private Resource klantenResource;
    @Value("classpath:/fxml/magazijnier/BestellingenView.fxml")
    private Resource bestellingenResource;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GuiBuilder.selectedButtonBold(btnKlanten, Arrays.asList(btnBestellingen));
        GuiBuilder.setCenter(applicationContext, mainView, klantenResource);
    }
    @FXML
    public void logout(ActionEvent actionEvent) {
        log.info("User {} has been logged out.", userContext.getApplicationUser().getEmail());
        userContext.setApplicationUser(null);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GuiBuilder.changeScene(applicationContext, stage, loginResource, "Portal Login");
    }
    @FXML
    private void handleButtonKlanten(ActionEvent event) {
        GuiBuilder.selectedButtonBold(btnKlanten, Arrays.asList(btnBestellingen));
        GuiBuilder.setCenter(applicationContext, mainView, klantenResource);
    }
    @FXML
    private void handleButtonBestellingen(ActionEvent event) {
        GuiBuilder.selectedButtonBold(btnBestellingen, Arrays.asList(btnKlanten));
        GuiBuilder.setCenter(applicationContext, mainView, bestellingenResource);
    }
    public void setPaneRight(Resource resource) {
        GuiBuilder.setRight(applicationContext, mainView, resource);
    }
    public void removePaneRight() {
        GuiBuilder.removeRight(mainView);
    }
}
