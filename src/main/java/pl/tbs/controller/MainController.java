package pl.tbs.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import pl.tbs.model.LogDataModel;
import pl.tbs.model.SettingsDataModel;
import pl.tbs.model.StudentDataModel;

public class MainController {

    private SettingsManager settingsManager;
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

    private SettingsDataModel settingsDM;

    public void initialize() {
        settingsManager = SettingsManager.INSTANCE;

        //open settings window if domain is not set
        if (!settingsDM.isDomainControllerSet()) {
            menuBarController.onSettingsMenuItem();
        }
    }

    //initalize data models in controllers, singletons are initialized in App.java
    public void initDM(StudentDataModel studentDM, LogDataModel logDM, SettingsDataModel settingsDM) {
        this.settingsDM = settingsDM;
        tableViewController.initModel(studentDM);
        actionsBarController.initModel(studentDM, settingsDM);
        menuBarController.initModel(studentDM, settingsDM);
        filterBarController.initModel(studentDM);
        logController.initDM(logDM);

    }


        // TextInputDialog dialog = new TextInputDialog("server.domain.com");
        // dialog.setTitle("Domain Controller server");
        // dialog.setHeaderText("Enter domain controller server name");

        // Optional<String> result = dialog.showAndWait();

        // if (result.isPresent()) {
        //     settingsDM.setDomainController(result.get());
        //     settingsManager.saveSettings();
    }

    
}
