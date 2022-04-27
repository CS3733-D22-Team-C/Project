package edu.wpi.cs3733.D22.teamC.controller.service_request.landing_page;


import edu.wpi.cs3733.D22.teamB.api.API;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamB.api.DatabaseController;
import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance.ServiceException;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceRequestAPIScrollPane implements Initializable {

    Label[] allLabels;

    @FXML private Label teamB;
    @FXML private Label teamC;
    @FXML private Label teamZ;

    DatabaseController databaseController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allLabels = new Label[]{teamC, teamB, teamZ};

        //Not sure what we want to do with this
        //Loads our locations and employees into team B
        databaseController = new DatabaseController();
//        try {
//            databaseController.reset();
//        } catch (edu.wpi.cs3733.D22.teamB.api.ServiceException e) {
//            e.printStackTrace();
//        }


    }

    public void loadEmployeesTeamB(){
        List<Employee> employees = new EmployeeDAO().getAll();
        for(Employee employee: employees){
            databaseController.addEmployee(employee.getLastName(), employee.getFirstName(), "", employee.getRole().toString());
        }
    }

    public void loadLocationsTeamB(){
                List<Location> locations = new LocationDAO().getAll();
        for(Location location: locations){
            databaseController.add(new edu.wpi.cs3733.D22.teamB.api.Location(location.getID(), location.getX(), location.getY(), location.getFloor().getID(), location.getBuilding(), location.getNodeType().toString(), location.getLongName(),location.getShortName()));
        }

    }

    /**
     * Sets the visibility of the name labels
     * @param canSee true if you can see the names, false if you can't see the names.
     */
    protected void changeNameVisibility(boolean canSee) {
        for (Label label : allLabels) {
            label.setVisible(canSee);
        }
    }

    @FXML
    void clickTeamC() throws ServiceException, IOException {
        TeamCAPI tC = new TeamCAPI();

        List<Employee> employees = new EmployeeDAO().getAll();
        for(Employee employee: employees){
            if(tC.getEmployee(employee.getFirstName(), employee.getLastName()) == null)
                tC.createEmployee(employee.getLastName(), employee.getFirstName());
        }
        tC.setOwner(App.instance.getStage());
        tC.run(50, 50, 1000, 1000, "../../css/default.css", "Floor 1", "Floor 2");
    }

    @FXML
    void clickTeamB(MouseEvent event) throws edu.wpi.cs3733.D22.teamB.api.ServiceException, IOException {
        //Running the API
        edu.wpi.cs3733.D22.teamB.api.API apiB = new edu.wpi.cs3733.D22.teamB.api.API();
        apiB.run(0,0,700,700,"edu/wpi/cs3733/D22/teamC/css/default.css", "hello", "goodbye");
    }

    @FXML
    void clickTeamZ(MouseEvent event) throws edu.wpi.cs3733.D22.teamZ.api.exception.ServiceException {
        edu.wpi.cs3733.D22.teamZ.api.API apiZ = new edu.wpi.cs3733.D22.teamZ.api.API();
        apiZ.run(60,60,700,700,"edu/wpi/cs3733/D22/teamC/css/default.css", "hello", "goodbye");
    }

    //TODO: Add other APIs here
}
