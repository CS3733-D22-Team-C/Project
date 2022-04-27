package edu.wpi.cs3733.D22.teamC.controller.general.login_page;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.SessionManager;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.login.LoginErrorItem;
import edu.wpi.cs3733.D22.teamC.validation.login.LoginEvaluator;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static io.github.palexdev.materialfx.validation.Validated.INVALID_PSEUDO_CLASS;

public class LoginPageController implements Initializable {
    @FXML
    private MFXTextField username;

    @FXML
    private MFXPasswordField password;

    @FXML
    private MFXToggleButton toggleButton;

    @FXML
    private Label invalidLogin;

    @FXML
    private Label usernameValidationLabel;

    @FXML
    private Label passwordValidationLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        invalidLogin.setVisible(false);
        // Set the limit on the text
        username.setTextLimit(20);
        password.setTextLimit(20);

        // Create constraints for both fields
        Constraint usernameFill = Constraint.Builder.build()
                        .setSeverity(Severity.ERROR)
                        .setMessage("Please enter a username.")
                        .setCondition(username.textProperty().length().greaterThanOrEqualTo(1))
                        .get();

        Constraint passwordFill = Constraint.Builder.build()
                        .setSeverity(Severity.ERROR)
                        .setMessage("Please enter a password.")
                        .setCondition(password.textProperty().length().greaterThanOrEqualTo(1))
                        .get();

        // Assign constraints to fields
        username.getValidator().constraint(usernameFill);
        password.getValidator().constraint(passwordFill);

        // Add listeners for both fields
        username.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                usernameValidationLabel.setVisible(false);
                username.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
                username.getStyleClass().remove("validated-field");
                invalidLogin.setVisible(false);
            }
        });

        username.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraints = username.validate();
                if (!constraints.isEmpty()) {
                    username.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                    usernameValidationLabel.setText(constraints.get(0).getMessage());
                    usernameValidationLabel.setVisible(true);
                    username.getStyleClass().add("validated-field");
                }
            }
        });

        password.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordValidationLabel.setVisible(false);
                password.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
                password.getStyleClass().remove("validated-field");
                invalidLogin.setVisible(false);
            }
        });

        password.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraints = password.validate();
                if (!constraints.isEmpty()) {
                    password.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                    passwordValidationLabel.setText(constraints.get(0).getMessage());
                    passwordValidationLabel.setVisible(true);
                    password.getStyleClass().add("validated-field");
                }
            }
        });

        setInitialSelection();
        toggleButton.setOnAction(e -> checkSelection());
    }

    public void setInitialSelection() {
        if (SessionManager.getServerDatabase() == SessionManager.DBMode.SERVER) {
            toggleButton.setSelected(true);
            toggleButton.setText("Switch to Embedded");
        } else {
            toggleButton.setSelected(false);
            toggleButton.setText("Switch to Server");
        }
    }

    public void checkSelection() {
        if (toggleButton.isSelected()) {
            toggleButton.setText("Switch to Embedded");
            SessionManager.switchDatabase(SessionManager.DBMode.SERVER);
        } else {
            toggleButton.setText("Switch to Server");
            SessionManager.switchDatabase(SessionManager.DBMode.EMBEDDED);
        }
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) {
        // If the username (with any password) is gamer, Color Collision is launched
        if(username.getText().equals("gamer")){
            try
            {
                ProcessBuilder pb = new ProcessBuilder();
                File dir = new File("Game\\Color-Collision-Final");
                pb.directory(dir);
                pb.command("Game\\Color-Collision-Final\\Color-Collision.exe");
                pb.start();
                return;
            }

            catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
        }
        LoginEvaluator loginEV = new LoginEvaluator();
        EmployeeDAO eDAO = new EmployeeDAO();

        ArrayList<LoginErrorItem> errors = loginEV.getLoginValidationTestResult(username.getText(), password.getText(), eDAO);

        if(errors.get(0) != null) {
            prepareLoginErrorMessage(errors.get(0));
            password.setText("");
        }
        else {
            App.instance.setUserAccount(eDAO.getEmployeeByUsername(username.getText()));
            App.instance.setView(App.DASHBOARD_PATH);
            App.instance.showMenuBar(true);
        }
    }

    @FXML
    public void exitButtonClicked(ActionEvent event) {
        App.instance.getStage().close();
    }

    public void prepareLoginErrorMessage(LoginErrorItem i) {
        invalidLogin.setText(i.getReasonForValidationError());
        invalidLogin.setAlignment(Pos.CENTER);
        invalidLogin.setVisible(true);
    }
}
