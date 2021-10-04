package pl.tbs;

import org.controlsfx.control.NotificationPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("main.fxml"));
        
        VBox mainWindow = fxmlLoader.<VBox>load();
        NotificationPane notificationPane = new NotificationPane(mainWindow);
        
        Scene scene = new Scene(notificationPane);
        FileChooser fileChooser = new FileChooser();
        MainController mainController = fxmlLoader.getController();
        mainController.initFileChooser(fileChooser);


        stage.setTitle("AD password toolset - TBS Warsaw");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}