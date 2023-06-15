package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
public class KlantenAddController extends VBox implements Initializable {
    private Customer customer;
    @Autowired
    private KlantenModel klantenModel;
    @Autowired
    private _MagazijnierMenuController magazijnierMenuController;
    @FXML
    private TextField naam;
    @FXML
    private TextField adresStraat;
    @FXML
    private TextField adresNummer;
    @FXML
    private TextField adresPostcode;
    @FXML
    private TextField adresLand;
    @FXML
    private TextField telefoonnummer;
    @FXML
    private ImageView logo;
    private byte[] logoBytes;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customer = new Customer();
    }

    @FXML
    private void annuleerBewerking(){
        magazijnierMenuController.removePaneRight();
    }
    @FXML
    private void opslaan(){
        if (logoBytes != null)
            customer.setLogo(logoBytes);
        customer.setName(naam.getText());

        Address address = new Address();
        address.setStreet(adresStraat.getText());
        address.setHouseNumber(adresNummer.getText());
        address.setPostalCode(adresPostcode.getText());
        address.setCountry(adresLand.getText());

        customer.setAddress(address);
        customer.setPhoneNumber(telefoonnummer.getText());
        customer.setBuyers(new ArrayList<Person>());

        klantenModel.addKlant(customer);
        klantenModel.updateKlanten();
        magazijnierMenuController.removePaneRight();
    }

    @FXML
    private void uploadImageActionPerformed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            logoBytes = Files.readAllBytes(file.toPath());
            BufferedImage newImg = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            newImg.createGraphics().drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);

            int[] type_int_agrb = ((DataBufferInt) newImg.getRaster().getDataBuffer()).getData();
            IntBuffer buffer = IntBuffer.wrap(type_int_agrb);

            PixelFormat<IntBuffer> pixelFormat = PixelFormat.getIntArgbPreInstance();
            PixelBuffer<IntBuffer> pixelBuffer = new PixelBuffer(newImg.getWidth(), newImg.getHeight(), buffer, pixelFormat);
            logo.setImage(new WritableImage(pixelBuffer));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
