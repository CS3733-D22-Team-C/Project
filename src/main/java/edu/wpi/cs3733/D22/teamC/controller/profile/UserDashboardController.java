package edu.wpi.cs3733.D22.teamC.controller.profile;

import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.component.sidebar.DrawerContentController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.BaseServiceRequestResolveController;
import edu.wpi.cs3733.D22.teamC.controller.service_request.SegmentBarController;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.utils.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
import java.util.List;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {
    private static final String FROM_DASH_RESOLVE_FORM = "view/service_request/skeleton/from_dash_resolve.fxml";

    @FXML private JFXTreeTableView assignedTable;
    @FXML private JFXTreeTableView createdTable;
    @FXML private Label greetingLabel;
    @FXML private VBox assignedTableBox;
    @FXML private VBox createdTableBox;

    @FXML Label position;
    @FXML Label contact;
    @FXML Label address;
    @FXML Label username;
    @FXML MFXButton submitButton;
    @FXML MFXButton changePasswordButton;

    @FXML ImageView profileImage;
    @FXML MFXButton addimg;

    @FXML MFXPasswordField beforePassword;
    @FXML MFXPasswordField newPass;
    @FXML MFXPasswordField newPassConfirm;

    //ones made in the whatever
    @FXML VBox mainNode;
    @FXML VBox passwordNode;

    private Employee employee;
    private DashboardAssignedTableDisplay assignedTableDisplay;
    private DashboardCreatedTableDisplay createdTableDisplay;

    SegmentBarController insertAssignedTableBarController;
    SegmentBarController insertCreatedTableBarController;



    Employee currentEmploy;
    private byte[] bFile;
    private String imagePath;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentEmploy = App.instance.getUserAccount();
        if (currentEmploy.getImage() != null) {
            ByteArrayInputStream bis = new ByteArrayInputStream(currentEmploy.getImage());
            BufferedImage img = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(img, null);
            profileImage.setImage(image);
        }

        //Set label texts
        setGreetingLabel(currentEmploy);
//        position.setText(currentEmploy.getRole().toString());
        contact.setText(currentEmploy.getPhone());
        address.setText(currentEmploy.getAddress());
        username.setText(currentEmploy.getUsername());

        // Do table stuff
        assignedTableDisplay = new DashboardAssignedTableDisplay(assignedTable);
        createdTableDisplay = new DashboardCreatedTableDisplay(createdTable);

        setOtherRowInteraction();
        setRowInteraction();

        // Populate Table Display
        ServiceRequestDAO serviceRequestDAO  = new ServiceRequestDAO();

        List<ServiceRequest> assignedServiceRequests = serviceRequestDAO.getAllSRByAssignee(App.instance.getUserAccount().getID());
        assignedServiceRequests.forEach(assignedTableDisplay::addObject);
        List<ServiceRequest> createdServiceRequests = serviceRequestDAO.getAllSRByCreator(App.instance.getUserAccount().getID());
        createdServiceRequests.forEach(createdTableDisplay::addObject);

        setGreetingLabel(currentEmploy);

        //SegmentedBars
        setAssignedTableSegmentedBarInsert();
        insertAssignedTableBarController.preSetup();
        for (ServiceRequest serviceRequest : assignedServiceRequests) {
            insertAssignedTableBarController.updateNumbers(serviceRequest.getStatus(), true);
        }
        insertAssignedTableBarController.setup(true);

        setCreatedTableSegmentedBarInsert();
        insertCreatedTableBarController.preSetup();
        for (ServiceRequest serviceRequest : createdServiceRequests) {
            insertCreatedTableBarController.updateNumbers(serviceRequest.getStatus(), true);
        }
        insertCreatedTableBarController.setup(true);

        passwordNode.setVisible(false);
    }

    @FXML
    public void onChangePasswordButtonPress() {
        passwordNode.setVisible(true);
        mainNode.setVisible(false);
    }

    @FXML
    public void onSubmitButtonPress() {
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
            passwordNode.setVisible(false);
            mainNode.setVisible(true);
        } else {
            beforePassword.setText("");
            newPass.setText("");
            newPassConfirm.setText("");
            submitButton.setText("Try Again");
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

                profileImage.setImage(image);

                currentEmploy.setImage(bFile);
                new EmployeeDAO().update(currentEmploy);
                //new EmployeeDAO().update();



            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGreetingLabel(Employee employee) {
        greetingLabel.setText(employee.getFirstName() + " " + employee.getLastName());
    }

    public void setAssignedTableSegmentedBarInsert(){
        App.View<SegmentBarController> view = App.instance.loadView("view/service_request/segment_bar.fxml");
        insertAssignedTableBarController = view.getController();
        assignedTableBox.getChildren().add(0, view.getNode());
    }

    public void setCreatedTableSegmentedBarInsert(){
        App.View<SegmentBarController> view = App.instance.loadView("view/service_request/segment_bar.fxml");
        insertCreatedTableBarController = view.getController();
        createdTableBox.getChildren().add(0, view.getNode());
    }

    protected void setRowInteraction() {
        assignedTable.setRowFactory(tv -> {
            TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry> row = new TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        ServiceRequest SR = (ServiceRequest) row.getItem().object;
                        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(FROM_DASH_RESOLVE_FORM);
                        view.getController().setup(SR, false);
                        App.instance.setView(view.getNode());
                    }
                }
            });
            return row ;
        });
    }

    protected void setOtherRowInteraction() {
        createdTable.setRowFactory(tv -> {
            TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry> row = new TreeTableRow<ServiceRequestTableDisplay.ServiceRequestTableEntry>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                    if (event.getClickCount() == 2) {
                        ServiceRequest SR = (ServiceRequest) row.getItem().object;
                        App.View<BaseServiceRequestResolveController> view = App.instance.loadView(FROM_DASH_RESOLVE_FORM);
                        view.getController().setup(SR, true);
                        App.instance.setView(view.getNode());
                    }
                }
            });
            return row ;
        });
    }
}
