package edu.wpi.cs3733.D22.teamC;

import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;


@Slf4j
public class App extends Application {

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) {
    DBManager man = new DBManager();
  man.startup();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
