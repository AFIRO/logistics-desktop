package be.zetta.logisticsdesktop.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class GuiBuilder {
    public static void changeScene(ApplicationContext applicationContext, Stage stage, Resource resource, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();
            stage.setScene(new Scene(parent));
            stage.setMinHeight(1050);
            stage.setMinWidth(1920);
            stage.setMaximized(true);
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setCenter(ApplicationContext applicationContext, BorderPane bp, Resource resource) {
        try {
            FXMLLoader loader = new FXMLLoader(resource.getURL());
            loader.setControllerFactory(applicationContext::getBean);
            bp.setCenter(loader.load());
            bp.setRight(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setRight(ApplicationContext applicationContext, BorderPane bp, Resource resource) {
        try {
            FXMLLoader loader = new FXMLLoader(resource.getURL());
            loader.setControllerFactory(applicationContext::getBean);
            bp.setRight(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void selectedButtonBold(Button selectedButton, List<Button> unSelectedButtons) {
        selectedButton.setStyle("-fx-font-weight: bold");
        unSelectedButtons.forEach(button -> button.setStyle("-fx-font-weight: normal"));
    }

    public static void removeRight(BorderPane bp) {
        bp.setRight(null);
    }
}
