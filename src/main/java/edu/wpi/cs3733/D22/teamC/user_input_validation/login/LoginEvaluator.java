package edu.wpi.cs3733.D22.teamC.user_input_validation.login;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.login_user_input_validation_error_item.LoginUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.login_user_input_validation.LoginUserInputValidationErrorRecord;

import java.util.ArrayList;

public class LoginEvaluator {

    public LoginEvaluator() {}

    public ArrayList<LoginUserInputValidationErrorItem> getLoginValidationTestResult(String username, String password)
    {
        ArrayList <LoginUserInputValidationErrorItem> l = new ArrayList <LoginUserInputValidationErrorItem> ();
        l.add(checkValidLogin(username, password));

        return l;
    }

    public LoginUserInputValidationErrorItem checkValidLogin(String username, String password)
    {
        if(username.equals("staff") && password.equals("staff"))
        {
            return null;
        }
        else if(username.equals("admin") && password.equals("admin"))
        {
            return null;
        }
        else
        {
            return LoginUserInputValidationErrorRecord.loginUserInputValidationErrorList[0];
        }
    }



}
