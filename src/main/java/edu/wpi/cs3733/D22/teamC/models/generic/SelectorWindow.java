package edu.wpi.cs3733.D22.teamC.models.generic;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeSelectorWindow;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public abstract class SelectorWindow<T> {
    // Variables
    Consumer<T> returnConsumer;
    Stage stage;

    public SelectorWindow(Consumer<T> consumer) {
        App.View<SelectorWindow<T>> view = App.instance.loadView(getView(), this);
        Parent root = (Parent) view.getNode();

        // Set Window
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        if (scene != null) scene.setRoot(root);
        else scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(App.instance.getStage().getScene().getWindow());
        stage.show();

        // Set Controller
        SelectorWindow<T> controller = view.getController();
        controller.returnConsumer = consumer;
        controller.stage = stage;
    }

    public void onSelectionMade(T object) {
        returnConsumer.accept(object);
        stage.close();
    }

    protected abstract String getView();
}
