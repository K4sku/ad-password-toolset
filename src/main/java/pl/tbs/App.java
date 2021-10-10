package pl.tbs;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("main.fxml"));
        scene = new Scene(fxmlLoader.load());

        stage.setTitle("AD password toolset - TBS Warsaw");
        stage.setScene(scene);
        // MainController mainC = fxmlLoader.getController();

        // Action testAction = new Action("test") {
        //     { setEventHandler(this::handleAction); }
        //     private void handleAction(ActionEvent ae) {
        //         System.out.println( "Action 1.1 is executed");
        //     }
        // };
        // Button testActionButton = ActionUtils.createButton(testAction);
        // scene.getRoot().getChildren().add(testActionButton);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}