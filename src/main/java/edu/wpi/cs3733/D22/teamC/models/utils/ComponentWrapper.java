package edu.wpi.cs3733.D22.teamC.models.utils;

import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ComponentWrapper {
    public static <T> void InitializeComboBox(ComboBox<T> comboBox, Callback<T, String> displayCallback) {
        Callback<ListView<T>, ListCell<T>> factory = lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : displayCallback.call(item));
            }
        };
        comboBox.setCellFactory(factory);
        comboBox.setButtonCell(factory.call(null));
    }
}
