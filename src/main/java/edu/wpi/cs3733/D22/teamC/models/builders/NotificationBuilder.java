package edu.wpi.cs3733.D22.teamC.models.builders;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.geometry.Pos;
import javafx.scene.Node;
import org.controlsfx.control.Notifications;

public class NotificationBuilder {
    public static void createNotification(String title, String message) {
        Notifications
                .create()
                .owner(App.instance.getStage())
                .title(title)
                .text(message)
                .position(Pos.BOTTOM_RIGHT)
                .threshold(5, Notifications.create().title("Moved Medical Equipments"))
                .show();
    }
}
