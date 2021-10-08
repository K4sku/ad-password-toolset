package pl.tbs;

import java.io.IOException;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionUtils;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
        MenuBarProxy menuBar = new MenuBarProxy(stage, notificationPane);

        VBox layoutVBox = new VBox(menuBar.getMenuBar(), notificationPane);
        
        Scene scene = new Scene(layoutVBox);
        
        MainController mainController = fxmlLoader.getController();

        // Action testAction = new Action("Test Action 1") {
        //     (setEventHandler(this::handleAction)
        // };
        
        Action testAction = new Action("test") {
            { setEventHandler(this::handleAction); }
            
            private void handleAction(ActionEvent ae) {
                System.out.println( "Action 1.1 is executed");
            }
            
        };
        
        Button testActionButton = ActionUtils.createButton(testAction);
        mainWindow.getChildren().add(testActionButton);

        stage.setTitle("AD password toolset - TBS Warsaw");
        stage.setScene(scene);
        stage.show();
    }

    // @ActionProxy(text="Action 1.1")
    // private void action11() {
    //      System.out.println( "Action 1.1 is executed");
    // }

    public static void main(String[] args) {
        launch();
    }

}