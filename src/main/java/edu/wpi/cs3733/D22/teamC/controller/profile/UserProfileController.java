package edu.wpi.cs3733.D22.teamC.controller.profile;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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

    @FXML ImageView image;


    //ones made in the whatever
    @FXML VBox mainNode;
    @FXML VBox passwordNode;

    Employee currentEmploy;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        currentEmploy = App.instance.getUserAccount();
        name.setText(currentEmploy.getLastName() + " " + currentEmploy.getLastName());
        position.setText(currentEmploy.getRole().toString());
        contact.setText(currentEmploy.getAddress());
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
}
