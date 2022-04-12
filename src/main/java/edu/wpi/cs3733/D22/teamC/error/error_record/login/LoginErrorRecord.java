package edu.wpi.cs3733.D22.teamC.error.error_record.login;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input.login.LoginErrorItem;

public class LoginErrorRecord {
    public static final LoginErrorItem[] loginUserInputValidationErrorList = {
            new LoginErrorItem(16, "Invalid username or password")
    };
}
