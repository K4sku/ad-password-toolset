package pl.tbs;

import javafx.fxml.FXML;

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
    private LoggerController loggerController;

    public void initialize(){
        menuBarController.dInjection(loggerController, tableViewController);
    }
    
}
