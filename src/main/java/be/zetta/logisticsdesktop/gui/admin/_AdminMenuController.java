package be.zetta.logisticsdesktop.gui.admin;

import be.zetta.logisticsdesktop.domain.user.context.UserContext;
import be.zetta.logisticsdesktop.gui.GuiBuilder;
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
public class _AdminMenuController implements Initializable {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserContext userContext;
    @FXML
    private Button btnMedewerkers;
    @FXML
    private Button btnTransport;
    @FXML
    private Button btnDozen;
    @FXML
    private BorderPane mainView;
    @Value("classpath:/fxml/Login.fxml")
    private Resource loginResource;
    @Value("classpath:/fxml/admin/Medewerkers.fxml")
    private Resource medewerkersResource;
    @Value("classpath:/fxml/admin/TransportView.fxml")
    private Resource transportResource;
    @Value("classpath:/fxml/admin/Dozen.fxml")
    private Resource dozenResource;
    @FXML
    public void logout(javafx.event.ActionEvent actionEvent) {
        log.info("User {} has been logged out.", userContext.getApplicationUser().getEmail());
        userContext.setApplicationUser(null);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GuiBuilder.changeScene(applicationContext, stage, loginResource, "Portal Login");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GuiBuilder.selectedButtonBold(btnMedewerkers, Arrays.asList(btnDozen, btnTransport));
        GuiBuilder.setCenter(applicationContext, mainView, medewerkersResource);
    }
    @FXML
    private void handleButtonTransport() {
        GuiBuilder.selectedButtonBold(btnTransport, Arrays.asList(btnDozen, btnMedewerkers));
        GuiBuilder.setCenter(applicationContext, mainView, transportResource);
    }
    @FXML
    private void handleButtonDozen() {
        GuiBuilder.selectedButtonBold(btnDozen, Arrays.asList(btnMedewerkers, btnTransport));
        GuiBuilder.setCenter(applicationContext, mainView, dozenResource);
    }
    @FXML
    private void handleButtonMedewerkers() {
        GuiBuilder.selectedButtonBold(btnMedewerkers, Arrays.asList(btnDozen, btnTransport));
        GuiBuilder.setCenter(applicationContext, mainView, medewerkersResource);
    }

    public void setPaneRight(Resource resource) {
        GuiBuilder.setRight(applicationContext, mainView, resource);
    }

    public void removePaneRight() {
        GuiBuilder.removeRight(mainView);
    }
}
