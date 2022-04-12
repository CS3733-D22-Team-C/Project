package edu.wpi.cs3733.D22.teamC.controller.general.login_page;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.login_page.Login;
import edu.wpi.cs3733.D22.teamC.user_input_validation.login.LoginEvaluator;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    Label invalidLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTextLengthLimiter(username, 5);
        setTextLengthLimiter(password, 5);
        invalidLogin.setVisible(false);
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) {
        //Login Validation eventually needed here
        LoginEvaluator loginEV = new LoginEvaluator();

        if(loginEV.getLoginValidationTestResult(username.getText(), password.getText()) != null) {
            insertLoginErrorMessage();
        }
        else
        {
            Login login = new Login();
            login.setPassword(password.getText());
            login.setUsername(username.getText());
            App.instance.setView(App.VIEW_SERVICE_REQUESTS_PATH);
         }


    }

    @FXML
    public void exitButtonClicked(ActionEvent event)
    {
        App.instance.getStage().close();
    }

    public void insertLoginErrorMessage()
    {
        invalidLogin.setText("Invalid username or password");
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
