package pl.tbs.controller;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.tbs.model.LogDataModel;
import pl.tbs.model.LogEntry;

public class LogController {
    @FXML
    private VBox log;
    @FXML
    private Label logLabel1;
    @FXML
    private Label logLabel2;
    @FXML
    private Label logLabel3;
    @FXML
    private Label logLabel4;
    @FXML
    private Label logLabel5;
    @FXML
    private Label logLabel6;

    private LogDataModel logDM;

    public void initDM(LogDataModel logDM) {
        // ensure model is set once
        if (this.logDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.logDM = logDM;

        logDM.gObservableList().addListener(new ListChangeListener<LogEntry>() {
            @Override
            public void onChanged(Change<? extends LogEntry> change) {
                change.next();
                update(change.getAddedSubList().get(0));
            }
        });

    }

    public void update(LogEntry logMsg) {
        logLabel6.setText(logLabel5.getText());
        logLabel5.setText(logLabel4.getText());
        logLabel4.setText(logLabel3.getText());
        logLabel3.setText(logLabel2.getText());
        logLabel2.setText(logLabel1.getText());
        logLabel1.setText(logMsg.toString());
    }

    // TODO
    // @FXML
    // private void onPopoutButton(){
    // try {
    // FXMLLoader fxmlLoader = new FXMLLoader();
    // fxmlLoader.setLocation(getClass().getResource("logTextFlow.fxml"));
    // // fxmlLoader.setController();
    // Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    // Stage stage = new Stage();
    // stage.setTitle("New Window");
    // stage.setScene(scene);
    // stage.show();
    // } catch (IOException e) {
    // // Logger logger = Logger.getLogger(getClass().getName());
    // // logger.log(Level.SEVERE, "Failed to create new Window.", e);
    // }
    // }
}
