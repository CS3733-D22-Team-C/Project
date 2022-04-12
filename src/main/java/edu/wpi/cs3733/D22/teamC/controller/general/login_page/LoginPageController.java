package edu.wpi.cs3733.D22.teamC.controller.general.login_page;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.component.sidebar.SidebarMenuController;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.login_user_input_validation_error_item.LoginUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.user_input_validation.login.LoginEvaluator;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    Label invalidLogin;

    @FXML
    MFXButton toggleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTextLengthLimiter(username, 20);
        setTextLengthLimiter(password, 20);
        invalidLogin.setVisible(false);
    }



    @FXML
    public void loginButtonClicked(ActionEvent event) {
        //Login Validation eventually needed here
        invalidLogin.setVisible(false);

        LoginEvaluator loginEV = new LoginEvaluator();
        EmployeeDAO eDAO = new EmployeeDAO();

        ArrayList<LoginUserInputValidationErrorItem> errors = loginEV.getLoginValidationTestResult(username.getText(), password.getText(), eDAO);

        if(errors.get(0) != null ){
            prepareLoginErrorMessage(errors.get(0));
        }
        else
        {
            App.instance.setUserAccount(eDAO.getEmployeeByUsername(username.getText()));
            App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
        }


    }

    @FXML
    public void exitButtonClicked(ActionEvent event)
    {
        App.instance.getStage().close();
    }

    public void prepareLoginErrorMessage(LoginUserInputValidationErrorItem i)
    {
        invalidLogin.setText(i.getReasonForValidationError());
        invalidLogin.setAlignment(Pos.CENTER);
        invalidLogin.setVisible(true);
    }

    private void setTextLengthLimiter(final TextField textF, final int maxLength) {
        textF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textF.getText().length() > maxLength) {
                    String s = textF.getText().substring(0, maxLength);
                    textF.setText(s);
                }
            }
        });
    }
}
