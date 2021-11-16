package pl.tbs.controller;

import javafx.fxml.FXML;
import pl.tbs.model.LogDataModel;
import pl.tbs.model.StudentDataModel;

public class MainController {

    @FXML
    private MenuBarController menuBarController;
    @FXML
    private FilterBarController filterBarController;
    @FXML
    private TableViewController tableViewController;
    @FXML
    private ActionsBarController actionsBarController;
    @FXML
    private LogController logController;

    //TODO decide if needed - do not forget initDM method too
    private StudentDataModel studentDM;
    private LogDataModel logDM;

    //initalize data models in controllers, singletons are initialized in App.java
    public void initDM(StudentDataModel studentDM, LogDataModel logDM){
        this.studentDM = studentDM;
        this.logDM = logDM;
        tableViewController.initModel(studentDM);
        actionsBarController.initModel(studentDM);
        menuBarController.initModel(studentDM);
        filterBarController.initModel(studentDM);
        logController.initDM(logDM);

    }

    
}
