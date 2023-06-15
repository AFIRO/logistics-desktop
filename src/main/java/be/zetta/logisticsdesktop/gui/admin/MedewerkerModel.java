package be.zetta.logisticsdesktop.gui.admin;

import be.zetta.logisticsdesktop.domain.medewerker.controller.MedewerkerController;
import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedewerkerModel {
    private List<Medewerker> medewerkers;
    private final ObservableList<MedewerkerData> tableItems = FXCollections.observableArrayList();
    @Autowired
    private MedewerkerController medewerkerController;
    public Medewerker getMedewerker(int index) {
        if (index >= 0)
            return medewerkers.get(index);
        return null;
    }
    public void updateMedewerkers(){
        medewerkers = medewerkerController.getAllMedewerkers();
        tableItems.clear();
        for (Medewerker medewerker : medewerkers){
            tableItems.add(new MedewerkerData(String.join(" ", medewerker.getPerson().getFirstName(), medewerker.getPerson().getLastName()), medewerker.getFunctie()));
        }
    }
    public ObservableList<MedewerkerData> getTableItems() {
        return tableItems;
    }
}
