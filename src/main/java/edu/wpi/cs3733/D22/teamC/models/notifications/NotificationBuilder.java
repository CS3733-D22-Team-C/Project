package edu.wpi.cs3733.D22.teamC.models.notifications;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import org.controlsfx.control.Notifications;

import java.util.ArrayList;
import java.util.List;

public class NotificationBuilder {
    public static void createNotification(String title, String message, Node owner) {
        Notifications
                .create()
                .owner(owner)
                .title(title)
                .text(message)
                .position(Pos.BOTTOM_RIGHT)
                .threshold(5, Notifications.create().title("Moved Medical Equipments"))
                .show();
    }
}
