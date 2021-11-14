package pl.tbs;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tbs.controller.Logger;
import pl.tbs.controller.MainController;
import pl.tbs.controller.PrinterAPI;
import pl.tbs.controller.XlsxLoader;
import pl.tbs.model.LogDataModel;
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
        mainController.initDM(studentDM, logDM);

        //setup singletons
        XlsxLoader.INSTANCE.initModel(studentDM);
        Logger.INSTANCE.initDM(logDM);
        PrinterAPI.INSTANCE.setupPrinter();

        //setup stage
        stage.setTitle("AD password toolset - TBS Warsaw");
        stage.setScene(scene);
        stage.setMinWidth(600.0);
        stage.setMinHeight(800.0);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}