package be.zetta.logisticsdesktop.gui.admin;

import be.zetta.logisticsdesktop.domain.packaging.controller.PackagingController;
import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.PackagingType;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DoosDetailController extends HBox implements Initializable {
    @Autowired
    private _AdminMenuController adminMenuController;
    @Autowired
    private PackagingController packagingController;
    @Autowired
    private DozenModel dozenModel;
    private Packaging doos;

    public void setDoos(Packaging doos) {
        this.doos = doos;
    }

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtWidth;
    @FXML
    private TextField txtHeight;
    @FXML
    private TextField txtLength;
    @FXML
    private RadioButton radioStandard;
    @FXML
    private RadioButton radioCustom;
    @FXML
    private RadioButton radioActiveYes;
    @FXML
    private RadioButton radioActiveNo;
    @FXML
    private Button btnDelete;
    @FXML
    private Label lblMessage;

    private final ToggleGroup groupType = new ToggleGroup();
    private final ToggleGroup groupActive = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioStandard.setToggleGroup(groupType);
        radioCustom.setToggleGroup(groupType);

        radioStandard.setSelected(true);
        radioActiveYes.setSelected(true);

        radioActiveYes.setToggleGroup(groupActive);
        radioActiveNo.setToggleGroup(groupActive);
        lblMessage.setText("");

        if(doos != null){
            txtName.setText(doos.getPackagingName());
            txtHeight.setText(String.valueOf(doos.getHeight()));
            txtLength.setText(String.valueOf(doos.getLength()));
            txtWidth.setText(String.valueOf(doos.getWidth()));
            txtPrice.setText(String.valueOf(doos.getPrice()));

            groupActive.selectToggle(doos.isActive() ? radioActiveYes : radioActiveNo);
            groupType.selectToggle(doos.getType() == PackagingType.STANDARD ? radioStandard : radioCustom);
            btnDelete.setDisable(false);
        }
        else{
            btnDelete.setDisable(true);
        }
    }

    @FXML
    void cancelEdit() {
        adminMenuController.removePaneRight();
        doos = null;
    }

    @FXML
    void deleteBox() {
        packagingController.deletePackaging(doos.getPackagingId());
        adminMenuController.removePaneRight();
        dozenModel.updateDozen();
        doos = null;
    }

    @FXML
    void saveBox() {
        PackagingType packagingType = groupType.getSelectedToggle() == radioStandard ? PackagingType.STANDARD : PackagingType.CUSTOM;
        boolean active = groupActive.getSelectedToggle() == radioActiveYes;

        try {Double.parseDouble(txtWidth.getText());} catch (NumberFormatException e){
            lblMessage.setText(e.getMessage());
            txtWidth.setText("0");
            return;
        }
        try {Double.parseDouble(txtHeight.getText());} catch (NumberFormatException e){
            lblMessage.setText(e.getMessage());
            txtHeight.setText("0");
            return;
        }
        try {Double.parseDouble(txtLength.getText());} catch (NumberFormatException e){
            lblMessage.setText(e.getMessage());
            txtLength.setText("0");
            return;
        }
        try {Double.parseDouble(txtPrice.getText());} catch (NumberFormatException e){
            lblMessage.setText(e.getMessage());
            txtPrice.setText("0");
            return;
        }
        
        try{
            Packaging newBox;
            PackagingDto packagingDto = new PackagingDto(null, txtName.getText(), packagingType, Double.parseDouble(txtHeight.getText()
            ), Double.parseDouble(txtWidth.getText()), Double.parseDouble(txtLength.getText()), Double.parseDouble(txtPrice.getText()), active);

            if(doos != null){
                packagingDto.setPackagingId(doos.getPackagingId());
                packagingController.updatePackaging(doos.getPackagingId(), packagingDto);
            }
            else {
                newBox = Packaging.builder()
                        .packagingName(txtName.getText())
                        .price(Double.parseDouble(txtPrice.getText()))
                        .height(Double.parseDouble(txtHeight.getText()))
                        .width(Double.parseDouble(txtWidth.getText()))
                        .length(Double.parseDouble(txtLength.getText()))
                        .active(active)
                        .type(packagingType)
                        .build();

                packagingDto.setPackagingId(newBox.getPackagingId());
                packagingController.createPackaging(packagingDto);

                adminMenuController.removePaneRight();
            }
        } catch (IllegalArgumentException e){
            lblMessage.setText(e.getMessage());
        }


        dozenModel.updateDozen();
    }
}
