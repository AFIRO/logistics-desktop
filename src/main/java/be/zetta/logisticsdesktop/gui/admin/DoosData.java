package be.zetta.logisticsdesktop.gui.admin;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.springframework.stereotype.Component;


@Component
public class DoosData {

    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleDoubleProperty height;
    private SimpleDoubleProperty width;
    private SimpleDoubleProperty length;
    private SimpleBooleanProperty active;
    private SimpleStringProperty type;

    DoosData(){}

    public DoosData(String name, Double price, Double height, Double width, Double length, boolean active, String type) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.height = new SimpleDoubleProperty(height);
        this.width = new SimpleDoubleProperty(width);
        this.length = new SimpleDoubleProperty(length);
        this.active = new SimpleBooleanProperty(active);
        this.type = new SimpleStringProperty(type);
    }

    public String getName() {
        return name.get();
    }

    public double getPrice() {
        return price.get();
    }

    public double getHeight() {
        return height.get();
    }

    public double getWidth() {
        return width.get();
    }

    public double getLength() {
        return length.get();
    }

    public boolean isActive() {
        return active.get();
    }

    public String getType() {
        return type.get();
    }

}

