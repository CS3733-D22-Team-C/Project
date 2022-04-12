package edu.wpi.cs3733.D22.teamC.error.error_item.user_input.login;

import edu.wpi.cs3733.D22.teamC.error.error_item.ErrorItem;

public class LoginErrorItem extends ErrorItem {

    public LoginErrorItem(int errorNumber, String reasonForValidationError) {
        super("User Input Validation", errorNumber, reasonForValidationError);
    }

}
