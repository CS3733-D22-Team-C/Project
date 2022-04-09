package edu.wpi.cs3733.D22.teamC.controller.table;

import edu.wpi.cs3733.D22.teamC.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseTableViewController2 implements Initializable {

    @FXML private VBox insertBox;
    private String PATH= "view/Table/Locations/table_insert.fxml";
    InserTableViewController insertController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("hello1");
        this.setUp();
    }

    public void setUp(){
        insertController = new InserTableViewController();
        System.out.println("hello2");
        setInsert(PATH);

    }

    public void setInsert(String path){
        App.View<InserTableViewController> view = App.instance.loadView(path);
        //insertController = view.getController();
        insertBox.getChildren().add(view.getNode());
    }
}
