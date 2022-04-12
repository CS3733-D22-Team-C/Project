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

    public LoginErrorItem checkValidLogin(String username, String password, EmployeeDAO eDAO)
    {
        //Need to get current employee and set it in the app
        if(eDAO.getEmployeeByUsername(username) == null)
        {
            return LoginErrorRecord.loginUserInputValidationErrorList[0];
        }
        else if(!(eDAO.getEmployeeByUsername(username).getPassword().equals(password)))
        {
            return LoginErrorRecord.loginUserInputValidationErrorList[0];
        }
        else
        {
            return null;
        }
    }



}
