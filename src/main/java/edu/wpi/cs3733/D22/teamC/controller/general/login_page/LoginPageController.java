package edu.wpi.cs3733.D22.teamC.controller.general.login_page;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.SessionManager;
import edu.wpi.cs3733.D22.teamC.controller.component.sidebar.SidebarMenuController;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.login.LoginErrorItem;
import edu.wpi.cs3733.D22.teamC.validation.login.LoginEvaluator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import org.sqlite.core.DB;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Label invalidLogin;

    @FXML
    private MFXToggleButton toggleButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) { //Initialize functions for the entirety of the controller's life
        setTextLengthLimiter(username, 20);
        setTextLengthLimiter(password, 20);
        invalidLogin.setVisible(false);
        toggleButton.setText("Switch to Server");

        if(toggleButton.isSelected()) { //isSelected() constantly checks if the toggle button has been selected, much more efficient and error-proof than checking the state of its text
            toggleButton.setOnAction(e -> toggleButton.setText("Switch to Embedded"));
            SessionManager.switchDatabase(SessionManager.DBMode.EMBEDDED);
        } else {
            toggleButton.setOnAction(e -> toggleButton.setText("Switch to Server"));
            SessionManager.switchDatabase(SessionManager.DBMode.SERVER);
        }
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) {
        //Login Validation eventually needed here
        invalidLogin.setVisible(false);

        LoginEvaluator loginEV = new LoginEvaluator();
        EmployeeDAO eDAO = new EmployeeDAO();

        ArrayList<LoginErrorItem> errors = loginEV.getLoginValidationTestResult(username.getText(), password.getText(), eDAO);

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

    public void prepareLoginErrorMessage(LoginErrorItem i)
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
