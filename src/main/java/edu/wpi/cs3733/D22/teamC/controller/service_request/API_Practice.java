
package edu.wpi.cs3733.D22.teamC.controller.service_request;


import edu.wpi.cs3733.D22.teamC.controller.service_request.facility_maintenance.ServiceExceptionAPI;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamZ.api.exception.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import edu.wpi.cs3733.D22.teamZ.api.API;
import edu.wpi.cs3733.D22.teamC.TeamCAPI;
import edu.wpi.cs3733.D22.teamB.api.DatabaseController;



import java.io.IOException;
import java.util.List;

public class API_Practice {

    @FXML
    void clickTeamBAPI(ActionEvent event) throws edu.wpi.cs3733.D22.teamB.api.ServiceException, IOException {
        //Adding Employees
        DatabaseController databaseController = new DatabaseController();
        databaseController.reset();
        List<Employee> employees = new EmployeeDAO().getAll();
        for(Employee employee: employees){
            databaseController.addEmployee(employee.getLastName(), employee.getFirstName(), "", employee.getRole().toString());
        }

        List<Location> locations = new LocationDAO().getAll();
        for(Location location: locations){
            edu.wpi.cs3733.D22.teamB.api.Location teamBLocation = new edu.wpi.cs3733.D22.teamB.api.Location(location.getID(), location.getX(), location.getY(), location.getFloor(), location.getBuilding(), location.getNodeType().toString(), location.getLongName(),location.getShortName());
        }


        //Running the API
        edu.wpi.cs3733.D22.teamB.api.API apiB = new edu.wpi.cs3733.D22.teamB.api.API();
        apiB.run(0,0,700,700,"", "hello", "goodbye");
    }

    @FXML
    void clickTeamZAPI(ActionEvent event) throws ServiceException {
        API apiZ = new API();
        apiZ.run(60,60,700,700,"edu/wpi/cs3733/D22/teamC/css/default.css", "hello", "goodbye");
    }

    @FXML
    void clickTeamCAPI(ActionEvent event) throws ServiceExceptionAPI, IOException {
        TeamCAPI apiC = new TeamCAPI();
        apiC.run(60,60,700,700,"", "hello", "goodbye");
    }
}
