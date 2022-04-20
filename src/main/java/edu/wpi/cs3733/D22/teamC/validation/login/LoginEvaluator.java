package edu.wpi.cs3733.D22.teamC.validation.login;

import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.login.LoginErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.login.LoginErrorRecord;

import java.util.ArrayList;

public class LoginEvaluator {

    public LoginEvaluator() {}

    public ArrayList<LoginErrorItem> getLoginValidationTestResult(String username, String password, EmployeeDAO eDAO)
    {
        ArrayList <LoginErrorItem> l = new ArrayList <LoginErrorItem> ();
        l.add(checkValidLogin(username, password, eDAO));

        return l;
    }

    public LoginErrorItem checkValidLogin(String username, String password, EmployeeDAO eDAO) {
        //Need to get current employee and set it in the app
        if (eDAO.getEmployeeByUsername(username) == null && !adminBackdoor(username, password)) {
            return LoginErrorRecord.loginUserInputValidationErrorList[0];
        }
        return null;
    }

    // Hardcoded backdoor to mitigate Employee database overrides... don't judge me.
    private boolean adminBackdoor(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            return true;
        } else return username.equals("staff") && password.equals("staff");
    }

}
