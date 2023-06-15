package be.zetta.logisticsdesktop.gui.admin.transport;

import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceExtraVerification;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TrackAndTraceTemplateDto;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class TransportData {

    private TransportDto transportDto;

    private final SimpleStringProperty transportId = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleBooleanProperty active = new SimpleBooleanProperty();
    private final ListProperty<PersonDto> contacts = new SimpleListProperty<>();
    private final SimpleStringProperty trackAndTracePrefix = new SimpleStringProperty();
    private final SimpleObjectProperty<TrackAndTraceExtraVerification> trackAndTraceTypeVerification = new SimpleObjectProperty();
    private final SimpleBooleanProperty trackAndTraceOnlyNumbers = new SimpleBooleanProperty();
    private final SimpleStringProperty trackAndTraceNumChars = new SimpleStringProperty();

    public TransportData(TransportDto transportDto) {
        this.transportDto = transportDto;
        this.setTransportData();
    }

    public TransportData(){

    }

    public void setTransportData(){

        if(transportDto != null){

            this.transportId.set(transportDto.getTransportId());
            this.name.set(transportDto.getTransportName());
            this.active.set(transportDto.isActive());

            if(transportDto.getContacts() != null)
                this.contacts.set(FXCollections.observableArrayList(transportDto.getContacts()));

            this.trackAndTracePrefix.set(transportDto.getTrackAndTraceTemplate().getPrefix());
            this.trackAndTraceTypeVerification.set(transportDto.getTrackAndTraceTemplate().getExtraVerification());
            this.trackAndTraceOnlyNumbers.set(transportDto.getTrackAndTraceTemplate().isOnlyNumbers());
            this.trackAndTraceNumChars.set(String.valueOf(transportDto.getTrackAndTraceTemplate().getNumberOfChars()));
        }
    }

    public TransportDto getCurrentTransportDto(){
        return this.transportDto;
    }

    public void setCurrentTransportDto(TransportDto transportDto){
        this.transportDto = transportDto;
        setTransportData();
    }

    public TransportDto getTransportDto(){

        this.transportDto = TransportDto.builder()
                .transportId(this.getTransportId())
                .transportName(this.getName())
                .active(this.isActive())
                .trackAndTraceTemplate(TrackAndTraceTemplateDto.builder()
                        .prefix(this.getTrackAndTracePrefix())
                        .onlyNumbers(this.isTrackAndTraceOnlyNumbers())
                        .numberOfChars(Integer.parseInt(this.getTrackAndTraceNumChars()))
                        .extraVerification(this.getTrackAndTraceTypeVerification())
                        .build())
                .build();

        return this.transportDto;
    }

    public String getName() {
        return name.get();
    }

    public String getTransportId() {
        return transportId.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public ListProperty<PersonDto> contactsProperty() {
        return contacts;
    }

    public String getTrackAndTracePrefix() {
        return trackAndTracePrefix.get();
    }

    public SimpleStringProperty trackAndTracePrefixProperty() {
        return trackAndTracePrefix;
    }

    public TrackAndTraceExtraVerification getTrackAndTraceTypeVerification() {
        return trackAndTraceTypeVerification.get();
    }

    public Property<TrackAndTraceExtraVerification> trackAndTraceTypeVerificationProperty() {
        return trackAndTraceTypeVerification;
    }

    public boolean isTrackAndTraceOnlyNumbers() {
        return trackAndTraceOnlyNumbers.get();
    }

    public SimpleBooleanProperty trackAndTraceOnlyNumbersProperty() {
        return trackAndTraceOnlyNumbers;
    }

    public String getTrackAndTraceNumChars() {
        return trackAndTraceNumChars.get();
    }

    public SimpleStringProperty trackAndTraceNumCharsProperty() {
        return trackAndTraceNumChars;
    }
}
