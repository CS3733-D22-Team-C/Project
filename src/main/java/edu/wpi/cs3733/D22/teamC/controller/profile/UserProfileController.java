package edu.wpi.cs3733.D22.teamC.controller.profile;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    @FXML Label name;
    @FXML Label position;
    @FXML Label contact;
    @FXML Label address;
    @FXML Label username;
    @FXML MFXButton back;
    @FXML MFXButton changePassword;
    @FXML VBox topNode;
    @FXML MFXButton submitChange;

    @FXML MFXPasswordField beforePassword;
    @FXML MFXPasswordField newPass;
    @FXML MFXPasswordField newPassConfirm;

    @FXML ImageView profileImage;
    @FXML MFXButton addimg;


    //ones made in the whatever
    @FXML VBox mainNode;
    @FXML VBox passwordNode;

    Employee currentEmploy;
    private byte[] bFile;
    private String imagePath;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        currentEmploy = App.instance.getUserAccount();
        if (currentEmploy.getImage() != null)
        {
            ByteArrayInputStream bis = new ByteArrayInputStream(currentEmploy.getImage());
            BufferedImage img = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(img, null);

//                profileImage.fitWidthProperty().bind(imageBox.widthProperty());
//                profileImage.fitHeightProperty().bind(imageBox.heightProperty());
            profileImage.setImage(image);
        }




        name.setText(currentEmploy.getFirstName() + " " + currentEmploy.getLastName());
        position.setText(currentEmploy.getRole().toString());
        contact.setText(currentEmploy.getPhone());
        address.setText(currentEmploy.getAddress());
        username.setText(currentEmploy.getUsername());
        topNode.setAlignment(Pos.TOP_CENTER);
        topNode.getChildren().remove(passwordNode);

    }

    public void backButton() {App.instance.setView(App.DASHBOARD_PATH);}

    public void changePasswordAction(){
        topNode.getChildren().remove(mainNode);
        topNode.getChildren().add(passwordNode);
        topNode.setAlignment(Pos.CENTER);
    }

    public void changePassword(){
        if (beforePassword.getText().equals(currentEmploy.getPassword()) &&
        newPass.getText().equals(newPassConfirm.getText()) && isLegal(newPass.getText())){
            //set the new password
            currentEmploy.setPassword(newPass.getText());
            new EmployeeDAO().update(currentEmploy);
            //Reset the fields
            beforePassword.setText("");
            newPass.setText("");
            newPassConfirm.setText("");
            //return the old node
            topNode.setAlignment(Pos.TOP_CENTER);
            topNode.getChildren().remove(passwordNode);
            topNode.getChildren().add(mainNode);
        } else {
            beforePassword.setText("");
            newPass.setText("");
            newPassConfirm.setText("");
            submitChange.setText("try again");
        }
    }

    public boolean isLegal(String password)
    {
        //The password paradox; the less popular a password is, the better it is.

        //What other parameters do we need?
        return password.length() <= 20;
    }

    public void addImage()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Image File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.jpg)", "*.png","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.instance.getStage());

        if (file != null) {
            // Load image
            try {
                BufferedImage bImg = ImageIO.read(file);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bImg, "png", byteArrayOutputStream);
                bFile = byteArrayOutputStream.toByteArray();
                imagePath = file.getName();

                ByteArrayInputStream bis = new ByteArrayInputStream(bFile);
                BufferedImage img = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(img, null);

//                profileImage.fitWidthProperty().bind(imageBox.widthProperty());
//                profileImage.fitHeightProperty().bind(imageBox.heightProperty());
                profileImage.setImage(image);

                currentEmploy.setImage(bFile);
                new EmployeeDAO().update(currentEmploy);
                //new EmployeeDAO().update();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
