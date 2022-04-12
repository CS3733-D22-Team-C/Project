package edu.wpi.cs3733.D22.teamC.user_input_validation.login;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.EmployeeDAO;
import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.login_user_input_validation_error_item.LoginUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.login_user_input_validation.LoginUserInputValidationErrorRecord;

import java.util.ArrayList;

public class LoginEvaluator {

    public LoginEvaluator() {}

    public ArrayList<LoginUserInputValidationErrorItem> getLoginValidationTestResult(String username, String password, EmployeeDAO eDAO)
    {
        ArrayList <LoginUserInputValidationErrorItem> l = new ArrayList <LoginUserInputValidationErrorItem> ();
        l.add(checkValidLogin(username, password, eDAO));

        return l;
    }

    public LoginUserInputValidationErrorItem checkValidLogin(String username, String password, EmployeeDAO eDAO)
    {
        //Need to get current employee and set it in the app
        if(eDAO.getEmployeeByUsername(username) == null)
        {
            return LoginUserInputValidationErrorRecord.loginUserInputValidationErrorList[0];
        }
        else if(!(eDAO.getEmployeeByUsername(username).getPassword().equals(password)))
        {
            return LoginUserInputValidationErrorRecord.loginUserInputValidationErrorList[0];
        }
        else
        {
            return null;
        }
    }



}
