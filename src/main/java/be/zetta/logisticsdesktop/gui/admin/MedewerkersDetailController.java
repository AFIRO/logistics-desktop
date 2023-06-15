package be.zetta.logisticsdesktop.gui.admin;

import be.zetta.logisticsdesktop.domain.medewerker.controller.MedewerkerController;
import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MedewerkersDetailController extends AnchorPane implements Initializable {
    @Autowired
    private _AdminMenuController adminMenuController;
    @Autowired
    private MedewerkerController medewerkerController;
    @Autowired
    private MedewerkerModel medewerkerModel;
    private Medewerker medewerker;

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
    }
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNo;
    @FXML
    private TextField txtStreet;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtCountry;
    @FXML
    private TextField txtStreetNo;
    @FXML
    private TextField txtBoxNo;
    @FXML
    private TextField txtPostalCode;
    @FXML
    private TextField txtPosition;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (medewerker != null) {
            txtFirstName.setText(medewerker.getPerson().getFirstName());
            txtLastName.setText(medewerker.getPerson().getLastName());
            txtPhoneNo.setText(medewerker.getPerson().getPhoneNumber());
            txtPostalCode.setText(medewerker.getPerson().getAddress().getPostalCode());
            txtCountry.setText(medewerker.getPerson().getAddress().getCountry());
            txtStreetNo.setText(medewerker.getPerson().getAddress().getHouseNumber());
            txtStreet.setText(medewerker.getPerson().getAddress().getStreet());
            txtPosition.setText(medewerker.getFunctie());
            txtEmail.setText(medewerker.getPerson().getEmail());
            btnDelete.setDisable(false);
        } else {
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void cancelEdit() {
        adminMenuController.removePaneRight();
        medewerker = null;
    }

    @FXML
    void deleteEmployee() {
        medewerkerController.deleteMedewerker(medewerker.getMedewerkerId());
        adminMenuController.removePaneRight();
        medewerkerModel.updateMedewerkers();
        medewerkerModel = null;
    }

    @FXML
    void saveEmployee() {
        Medewerker employee;
        Person employeeDetails;
        Address employeeAddress;

        AddressDto addressDto = new AddressDto(txtStreet.getText(), txtStreetNo.getText(), txtPostalCode.getText(), txtCountry.getText());
        PersonDto personDto = new PersonDto(null, txtFirstName.getText(), txtLastName.getText(), txtPhoneNo.getText(), txtEmail.getText(), addressDto);

        if(medewerker != null){
            personDto.setPersonId(medewerker.getMedewerkerId());
            medewerkerController.updateMedewerker(medewerker.getMedewerkerId(), new MedewerkerDto(personDto, txtPosition.getText()));
        }
        else {
            employeeAddress = Address.builder()
                    .country(txtCountry.getText())
                    .street(txtStreet.getText())
                    .houseNumber(txtStreetNo.getText())
                    .postalCode(txtPostalCode.getText())
                    .build();

            employeeDetails = Person.builder()
                    .firstName(txtFirstName.getText())
                    .lastName(txtLastName.getText())
                    .email(txtEmail.getText())
                    .phoneNumber(txtPhoneNo.getText())
                    .address(employeeAddress)
                    .build();


            employee = Medewerker.builder()
                    .person(employeeDetails)
                    .functie(txtPosition.getText())
                    .build();

            personDto.setPersonId(employee.getMedewerkerId());
            medewerkerController.createMedewerker(new MedewerkerDto(personDto, txtPosition.getText()));

            adminMenuController.removePaneRight();
        }
        medewerkerModel.updateMedewerkers();
        }

}
