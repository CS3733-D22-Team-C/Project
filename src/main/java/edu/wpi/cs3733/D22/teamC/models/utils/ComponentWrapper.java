package edu.wpi.cs3733.D22.teamC.models.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.util.Callback;

public class ComponentWrapper {
    //#region Combo Boxes
        public static <T> void initializeComboBox(ComboBox<T> comboBox, Callback<T, String> displayCallback) {
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

        public static <T> void setValueSilently(ComboBox<T> comboBox, T value) {
            EventHandler<ActionEvent> filter = e -> e.consume();
            comboBox.addEventFilter(ActionEvent.ACTION, filter);
            comboBox.setValue(value);
            comboBox.removeEventFilter(ActionEvent.ACTION, filter);
        }
    //#endregion

    //#region Text Input Fields
        public static void setIDFieldToNumeric(TextInputControl textField)
        {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        textField.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }

        public static void setTextLengthLimiter(final TextInputControl textField, final int maxLength) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                    if (textField.getText().length() > maxLength) {
                        String s = textField.getText().substring(0, maxLength);
                        textField.setText(s);
                    }
                }
            });
        }

}
