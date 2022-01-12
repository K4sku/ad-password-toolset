package pl.tbs;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tbs.controller.Logger;
import pl.tbs.controller.MainController;
import pl.tbs.controller.PrinterHandler;
import pl.tbs.controller.SettingsManager;
import pl.tbs.controller.XlsxHandler;
import pl.tbs.model.LogDataModel;
import pl.tbs.model.SettingsDataModel;
import pl.tbs.model.StudentDataModel;

/**
 * JavaFX App
 */
public class App extends Application {

    // private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //load nested FXMLs 
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController mainController = fxmlLoader.getController();
        
        //init data model and inject to controllers
        StudentDataModel studentDM = new StudentDataModel();
        LogDataModel logDM = new LogDataModel();
        SettingsDataModel settingsDM = new SettingsDataModel();
        mainController.initDM(studentDM, logDM, settingsDM);

        //setup singletons
        XlsxHandler.INSTANCE.initModel(studentDM);
        Logger.INSTANCE.initDM(logDM);
        PrinterHandler.INSTANCE.setupPrinter();
        SettingsManager.INSTANCE.initDM(settingsDM);

        //settings initialization
        SettingsManager.INSTANCE.initSettings();

        
        //setup stage
        stage.setTitle("AD password toolset");
        stage.setScene(scene);
        stage.setMinWidth(600.0);
        stage.setMinHeight(800.0);
        stage.show();
        

    }

    public static void main(String[] args) {
        launch();
    }

}