package pl.tbs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoggerController {
    @FXML private VBox logger;
    @FXML private Label logLabel1;
    @FXML private Label logLabel2;
    @FXML private Label logLabel3;
    @FXML private Label logLabel4;
    @FXML private Label logLabel5;
    @FXML private Label logLabel6;

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
}
