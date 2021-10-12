package pl.tbs.controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tbs.model.LogEntry;

public class LogController implements Log{
    @FXML private VBox logger;
    @FXML private Label logLabel1;
    @FXML private Label logLabel2;
    @FXML private Label logLabel3;
    @FXML private Label logLabel4;
    @FXML private Label logLabel5;
    @FXML private Label logLabel6;

    // @FXML
    // private TextFlow logTextFlow;
    // @FXML
    // private Button popoutButton;

    private ObservableList<LogEntry> observableLog = FXCollections.observableArrayList();

    public void initialize(){
        
    }

    public void add(LogEntry logMsg){
        observableLog.add(logMsg);
        logLabel6.setText(logLabel5.getText());
        logLabel5.setText(logLabel4.getText());
        logLabel4.setText(logLabel3.getText());
        logLabel3.setText(logLabel2.getText());
        logLabel2.setText(logLabel1.getText());
        logLabel1.setText(logMsg.toString());
    }

    // @FXML
    // private void onPopoutButton(){
    //     try {
    //         FXMLLoader fxmlLoader = new FXMLLoader();
    //         fxmlLoader.setLocation(getClass().getResource("logTextFlow.fxml"));
    //         // fxmlLoader.setController();
    //         Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    //         Stage stage = new Stage();
    //         stage.setTitle("New Window");
    //         stage.setScene(scene);
    //         stage.show();
    //     } catch (IOException e) {
    //         // Logger logger = Logger.getLogger(getClass().getName());
    //         // logger.log(Level.SEVERE, "Failed to create new Window.", e);
    //     }
    // }
}
