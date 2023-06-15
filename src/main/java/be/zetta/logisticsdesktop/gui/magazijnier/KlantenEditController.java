package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.util.ResourceBundle;

@Component
public class KlantenEditController extends VBox implements Initializable {
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
        }
    }

    @Value("classpath:/fxml/magazijnier/KlantenDetail.fxml")
    private Resource klantenDetailResource;
    @FXML
    private void annuleerBewerking(){
        magazijnierMenuController.setPaneRight(klantenDetailResource);
    }
    @FXML
    private void opslaan(){
        if (logoBytes != null)
            customer.setLogo(logoBytes);
        customer.setName(naam.getText());
        customer.getAddress().setStreet(adresStraat.getText());
        customer.getAddress().setHouseNumber(adresNummer.getText());
        customer.getAddress().setPostalCode(adresPostcode.getText());
        customer.getAddress().setCountry(adresLand.getText());
        customer.setPhoneNumber(telefoonnummer.getText());

        klantenModel.updateKlant(customer);
        klantenModel.updateKlanten();
        magazijnierMenuController.setPaneRight(klantenDetailResource);
    }
    @FXML
    private void verwijder() {
        klantenModel.deleteKlant(customer);
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
