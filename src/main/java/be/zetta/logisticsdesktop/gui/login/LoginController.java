package be.zetta.logisticsdesktop.gui.login;

import be.zetta.logisticsdesktop.domain.user.context.UserContext;
import be.zetta.logisticsdesktop.domain.user.controller.UserController;
import be.zetta.logisticsdesktop.domain.user.entity.dto.LoginDto;
import be.zetta.logisticsdesktop.gui.GuiBuilder;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController extends VBox {
    @Autowired
    private UserController userController;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserContext userContext;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private Label lblFouteCredentials;
    private Resource getLoginResource() {
        return new ClassPathResource("fxml/Login.fxml");
    }
    @Value("classpath:/fxml/magazijnier/_MagazijnierMenu.fxml")
    private Resource magazijnierResource;
    @Value("classpath:/fxml/admin/_AdminMenu.fxml")
    private Resource adminResource;

    @FXML
    public void login(javafx.event.ActionEvent actionEvent) {
        lblFouteCredentials.setVisible(false);
        boolean loggedIn = false;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (!(txtEmail.getText().isBlank() || txtPassword.getText().isBlank())) {
            try {
                loggedIn = userController.login(new LoginDto(txtEmail.getText(), txtPassword.getText()));
            } catch (IllegalArgumentException e) {
                lblFouteCredentials.setText(e.getMessage());
                lblFouteCredentials.setVisible(true);
            }
        }
        if (loggedIn) {
            switch (userContext.getApplicationUser().getRole()){
                case ADMIN -> GuiBuilder.changeScene(applicationContext, stage, adminResource, "Portaal Admin");
                case MAGAZIJNIER -> GuiBuilder.changeScene(applicationContext, stage, magazijnierResource, "Portaal Magazijnier");
                default -> {
                    lblFouteCredentials.setText("Er is geen rol geassocieerd met deze login");
                    lblFouteCredentials.setVisible(true);
                }
            }
        } else {
            lblFouteCredentials.setText("Uw credentials zijn onjuist.");
            lblFouteCredentials.setVisible(true);
        }
    }

    @FXML
    public void loginAdmin(javafx.event.ActionEvent actionEvent) {
        userController.login(new LoginDto("andreeas@hogent.be", "andreeas"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GuiBuilder.changeScene(applicationContext, stage, adminResource, "Portaal Admin");
    }

    @FXML
    public void loginMagazijnier(javafx.event.ActionEvent actionEvent) {
        userController.login(new LoginDto("kasper@hogent.be", "kasper"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        GuiBuilder.changeScene(applicationContext, stage, magazijnierResource, "Portaal Magazijnier");
    }
}
