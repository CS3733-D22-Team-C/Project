package edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.login_user_input_validation_error_item;

import edu.wpi.cs3733.D22.teamC.error.error_item.ErrorItem;

public class LoginUserInputValidationErrorItem extends ErrorItem {

    public LoginUserInputValidationErrorItem(int errorNumber, String reasonForValidationError) {
        super("User Input Validation", errorNumber, reasonForValidationError);
    }

}
