package be.zetta.logisticsdesktop.gui;

import be.zetta.logisticsdesktop.events.StageReadyEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GuiInitializer implements ApplicationListener<StageReadyEvent> {
    @Value("classpath:/fxml/Login.fxml")
    private Resource loginResource;
    private final String applicationTitle;
    private final ApplicationContext applicationContext;

    public GuiInitializer(@Value("Portal Login") String applicationTitle, ApplicationContext applicationContext) {
        this.applicationTitle = applicationTitle;
        this.applicationContext = applicationContext;
    }


    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(loginResource.getURL());
            fxmlLoader.setControllerFactory(loginController -> applicationContext.getBean(loginController));


            Parent parent = fxmlLoader.load();

            Stage primaryStage = event.getStage();
            primaryStage.setScene(new Scene(parent));
            primaryStage.setTitle(applicationTitle);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
