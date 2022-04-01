package edu.wpi.cs3733.D22.teamC.controller.component;

import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {
    // Canvas
    @FXML
    protected Canvas canvas;

    // Variables
    protected GraphicsContext graphicsContext;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        graphicsContext = canvas.getGraphicsContext2D();
        render();
    }

    private void render() {
        // Clear the Canvas
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.setFill(Color.BEIGE);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillOval(0, 0, 10, 10);
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillOval(500, 500, 10, 10);
    }

    private void drawPoints() {

    }
}
