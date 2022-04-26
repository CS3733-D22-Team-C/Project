package edu.wpi.cs3733.D22.teamC.models.generic;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.models.employee.EmployeeSelectorWindow;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.function.Consumer;

public abstract class SelectorWindow<T> {
    // Variables
    Consumer<T> returnConsumer;
    Stage stage;



    public SelectorWindow(Consumer<T> consumer) {
        this(consumer, App.instance.getActiveStage().getScene().getWindow());

    }

    public SelectorWindow(Consumer<T> consumer, Window owner) {
        App.View<SelectorWindow<T>> view = App.instance.loadView(getView(), this);
        Parent root = (Parent) view.getNode();



        // Set Window
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setOnCloseRequest(event -> {
            if(App.instance.getActiveStage() == stage){App.instance.removeActiveStage(stage);
        }});
        if (scene != null) scene.setRoot(root);
        else scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(owner);
        stage.show();
        stage.setHeight(700);
        stage.setWidth(700);


        App.instance.addActiveStage(stage);

        // Set Controller
        SelectorWindow<T> controller = view.getController();
        controller.returnConsumer = consumer;
        controller.stage = stage;
    }



    public void onSelectionMade(T object) {
        returnConsumer.accept(object);
        stage.close();
    }

    /**
     * Returns path to the view for the implementing Selector Window page.
     * @return Path to the view for the implementing Selector Window page.
     */
    protected abstract String getView();
}
