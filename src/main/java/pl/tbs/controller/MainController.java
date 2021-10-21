package pl.tbs.controller;

import javafx.fxml.FXML;
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
    private LogController loggerController;

    private StudentDataModel studentDM;

    public void initialize(){
        menuBarController.dInjection(loggerController, tableViewController);
        actionsBarController.dInjection(loggerController, tableViewController);



    }
    
    public void initDM(StudentDataModel studentDM){
        this.studentDM = studentDM;
        tableViewController.initModel(studentDM);
        actionsBarController.initModel(studentDM);
    }
    
}
