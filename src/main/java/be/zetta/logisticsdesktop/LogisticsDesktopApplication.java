package be.zetta.logisticsdesktop;

import be.zetta.logisticsdesktop.gui.GuiRunner;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogisticsDesktopApplication {
    public static void main(String[] args) {
        Application.launch(GuiRunner.class, args);
    }
}
