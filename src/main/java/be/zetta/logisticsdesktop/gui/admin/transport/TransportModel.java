package be.zetta.logisticsdesktop.gui.admin.transport;

import be.zetta.logisticsdesktop.domain.transport.controller.TransportController;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransportModel {

    private final ObservableList<TransportData> transportList = FXCollections.observableArrayList();

    @Autowired
    private TransportController transportController;

    public ObservableList<TransportData> getTableItems() {
        return transportList;
    }

    public void updateTransportTable(){
        List<TransportDto> transportDtoList = transportController.getAllTransports();
        transportList.clear();
        for (TransportDto transportDto : transportDtoList){
            transportList.add(new TransportData(transportDto));
        }
    }


    public void createTransport(TransportData transportData){
        if (transportData.getTransportId() != null && transportController.getById(transportData.getTransportId()) != null)
            transportController.updateTransport(transportData.getTransportId(), transportData.getTransportDto());
        else{
            TransportDto updatedDto = transportController.createTransport(transportData.getTransportDto());
            transportData.setCurrentTransportDto(updatedDto);
            transportList.add(transportData);

        }

    }

}
