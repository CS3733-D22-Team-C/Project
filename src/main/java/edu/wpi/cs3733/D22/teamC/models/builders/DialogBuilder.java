package edu.wpi.cs3733.D22.teamC.models.builders;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

import java.util.function.Consumer;

public class DialogBuilder {
    public static Alert createBinaryAlert(String title, String message) {
        // Create a dialog
        Alert dialog = new Alert(Alert.AlertType.NONE);

        // CSS Styling


        // Set Owner
        dialog.initOwner(App.instance.getStage());

        // Set the title
        dialog.setTitle(title);

        // Setting the content of the dialog
        dialog.setContentText(message);

        // Inner Content
        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

        dialog.getDialogPane().getButtonTypes().setAll(yesButton, noButton);



        return dialog;
    }


    public static Alert createAlertWithOptOut(Alert.AlertType type, String title, String headerText,
                                              String message, String optOutMessage, Consumer<Boolean> optOutAction,
                                              ButtonType... buttonTypes) {
        Alert alert = new Alert(type);

        alert.getDialogPane().applyCss();

        Node graphic = alert.getDialogPane().getGraphic();

        // Set Owner
        alert.initOwner(App.instance.getStage());

        // Overide Code adapted from https://stackoverflow.com/questions/36949595/how-do-i-create-a-javafx-alert-with-a-check-box-for-do-not-ask-again
        alert.setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                CheckBox optOut = new CheckBox();
                optOut.setText(optOutMessage);
                optOut.setOnAction(e -> optOutAction.accept(optOut.isSelected()));
                return optOut;
            }
        });
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        alert.getDialogPane().getStylesheets().add(App.class.getResource("css/default.css").toExternalForm());
        alert.getDialogPane().getButtonTypes().addAll(buttonTypes);

        alert.getDialogPane().setExpandableContent(new Group());
        alert.getDialogPane().setExpanded(true);

        alert.setTitle(title);
        alert.getDialogPane().setGraphic(graphic);
        alert.getDialogPane().setContentText(message);
        alert.setHeaderText(headerText);
        return alert;
    }
}
