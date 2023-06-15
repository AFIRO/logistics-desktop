package be.zetta.logisticsdesktop.gui.admin;

import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Component;


@Component
public class MedewerkerData {
    private SimpleStringProperty name;
    private SimpleStringProperty position;

    MedewerkerData(){}

    public MedewerkerData(String name, String position) {
        this.name = new SimpleStringProperty(name);
        this.position = new SimpleStringProperty(position);
    }

    public String getName() {
        return name.get();
    }

    public String getPosition() { return position.get(); }
}

