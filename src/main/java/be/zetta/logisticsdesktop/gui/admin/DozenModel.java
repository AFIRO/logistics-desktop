package be.zetta.logisticsdesktop.gui.admin;

import be.zetta.logisticsdesktop.domain.packaging.controller.PackagingController;
import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DozenModel {
    private List<Packaging> dozen;
    private final ObservableList<DoosData> tableItems = FXCollections.observableArrayList();
    @Autowired
    private PackagingController packagingController;
    public Packaging getDoos(int index) {
        // if (index < 0) return null; // check op zetten? bij aanmaak van nieuwe doos is er een out of bounds exception doordat -1 wordt doorgegeven bij deselecteren
        return dozen.get(index);
    }
    public void updateDozen(){
        dozen = packagingController.getAllPackaging();
        tableItems.clear();
        for (Packaging doos : dozen){
            tableItems.add(new DoosData(doos.getPackagingName(), doos.getPrice(), doos.getHeight(), doos.getWidth(), doos.getLength(), doos.isActive(), doos.getType().toString()));
        }
    }
    public ObservableList<DoosData> getTableItems() {
        return tableItems;
    }
}
