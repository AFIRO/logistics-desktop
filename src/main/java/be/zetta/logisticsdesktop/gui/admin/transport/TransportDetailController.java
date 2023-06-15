package be.zetta.logisticsdesktop.gui.admin.transport;


import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceExtraVerification;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import be.zetta.logisticsdesktop.gui.admin._AdminMenuController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class TransportDetailController extends HBox implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private CheckBox cbActive;
    @FXML
    private TextField txtNumberOfChars;
    @FXML
    private CheckBox cbOnlyNumbers;
    @FXML
    private ComboBox<TrackAndTraceExtraVerification> cboTypeVerification;
    @FXML
    private TextField txtPrefix;
    @FXML
    private TableView<PersonDto> tblContacts;
    @FXML
    private TableColumn<PersonDto, String> colFirstName;
    @FXML
    private TableColumn<PersonDto, String> colLastName;
    @FXML
    private TableColumn<PersonDto, String> colPhoneNumber;
    @FXML
    private TableColumn<PersonDto, String> colEmailAddress;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    private Label lblMessage;

    private TransportData transport;
    @Autowired
    private TransportModel transportModel;
    @Autowired
    private _AdminMenuController adminMenuController;

    private Boolean updateMode = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cboTypeVerification.getItems().setAll(TrackAndTraceExtraVerification.values());
        lblMessage.setText("");
        setViewFields(this.transport);

        setFieldsEditable(updateMode);

    }

    private void setViewFields(TransportData transport){

        if(transport != null) {
            txtName.textProperty().bindBidirectional(transport.nameProperty());
            cbActive.selectedProperty().bindBidirectional(transport.activeProperty());

            txtNumberOfChars.textProperty().bindBidirectional(transport.trackAndTraceNumCharsProperty());
            cbOnlyNumbers.selectedProperty().bindBidirectional(transport.trackAndTraceOnlyNumbersProperty());
            txtPrefix.textProperty().bindBidirectional(transport.trackAndTracePrefixProperty());
            cboTypeVerification.valueProperty().bindBidirectional(transport.trackAndTraceTypeVerificationProperty());

            colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            colEmailAddress.setCellValueFactory(new PropertyValueFactory<>("email"));

            tblContacts.getSelectionModel().clearSelection();
            tblContacts.setItems(transport.contactsProperty().get());

            cboTypeVerification.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(TrackAndTraceExtraVerification item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.name());
                }
            });

            cboTypeVerification.setButtonCell(new ListCell<TrackAndTraceExtraVerification>() {
                @Override
                protected void updateItem(TrackAndTraceExtraVerification item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.name());
                }
            });
        }

    }

    public void setTransportDetail(TransportData transport) {

        this.transport = transport;
        this.updateMode = false;
    }

    @FXML
    public void handleCancelButton() {
        setFieldsEditable(false);
        this.transport.setTransportData();
        lblMessage.setText("Update cancelled");
        adminMenuController.removePaneRight();
    }

    @FXML
    public void handleEditButton() {
        lblMessage.setText("");
        setFieldsEditable(true);
    }


    public void setNewTransport() {

        this.transport = new TransportData();
        this.transportModel.updateTransportTable();
        this.updateMode = true;

    }

    @FXML
    public void handleSaveTransport() {

        try {
            if(this.transport.getTransportId() == null)
                lblMessage.setText("Transport added");
            else
                lblMessage.setText("Transport updated");

            transportModel.createTransport(this.transport);
            setFieldsEditable(false);

        }catch (IllegalArgumentException exception){
            lblMessage.setText(exception.getMessage());
        }

    }

    private void setFieldsEditable(Boolean isEditable){
        txtName.setEditable(isEditable);
        cbActive.setDisable(!isEditable);

        txtNumberOfChars.setEditable(isEditable);
        cbOnlyNumbers.setDisable(!isEditable);
        txtPrefix.setEditable(isEditable);
        cboTypeVerification.setDisable(!isEditable);

        btnEdit.setDisable(isEditable);
        btnSave.setDisable(!isEditable);
        btnCancel.setDisable(!isEditable);
    }


}
