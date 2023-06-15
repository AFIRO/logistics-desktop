package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class KlantenDetailController extends VBox implements Initializable {
    private Customer customer;
    @Autowired
    private KlantenModel klantenModel;
    @Autowired
    private _MagazijnierMenuController magazijnierMenuController;
    @FXML
    private Label naam;
    @FXML
    private Label adresStraat;
    @FXML
    private Label adresNummer;
    @FXML
    private Label adresPostcode;
    @FXML
    private Label adresLand;
    @FXML
    private Label telefoonnummer;
    @FXML
    private ImageView logo;
    @FXML
    private TableView<Person> aankopers;
    @FXML
    private TableColumn<Person, String> aankoperNaam;
    @FXML
    private TableColumn<Person, String> aankoperMail;
    @FXML
    private TableView<CustomerOrder> bestellingen;
    @FXML
    private TableColumn<CustomerOrder, String> bestellingId;
    @FXML
    private TableColumn<CustomerOrder, String> bestellingDatum;
    @FXML
    private TableColumn<CustomerOrder, String> bestellingStatus;
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = klantenModel.getCustomerDetail();
        if (customer != null) {
            naam.setText(customer.getName());
            adresStraat.setText(customer.getAddress().getStreet());
            adresNummer.setText(customer.getAddress().getHouseNumber());
            adresPostcode.setText(customer.getAddress().getPostalCode());
            adresLand.setText(customer.getAddress().getCountry());
            telefoonnummer.setText((customer.getPhoneNumber()));
            if (customer.getLogo() != null)
                logo.setImage(new Image(new ByteArrayInputStream(customer.getLogo())));

            aankopers.setItems(klantenModel.getAankopers());
            aankoperNaam.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getFirstName() + " " + cellValue.getValue().getLastName()));
            aankoperMail.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getEmail()));

            bestellingen.setItems(klantenModel.getBestellingen());
            bestellingId.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getOrderId()));
            bestellingDatum.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getOrderDate().toString()));
            bestellingStatus.setCellValueFactory(cellValue -> new SimpleStringProperty(cellValue.getValue().getStatus().toString()));
        }
    }

    @Value("classpath:/fxml/magazijnier/KlantenEdit.fxml")
    private Resource klantenEditResource;
    @FXML
    private void editCurrentCustomer() {
        magazijnierMenuController.setPaneRight(klantenEditResource);
    }
}
