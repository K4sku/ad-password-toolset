package pl.tbs;

import static org.controlsfx.control.action.ActionMap.actions;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionGroup;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;

import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBarProxy {
    
    private Collection<? extends Action> actions;
    FileChooser fileChooser;
    
    Stage stage;
    File selectedFile;
    
    public MenuBarProxy(Stage stage) {
        ActionMap.register(this);
        this.stage = stage;
        actions = Arrays.asList(
            new ActionGroup("File", actions("openFileAction", "closeFileAction", "reloadFileAction"))
        );
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Spreadsheet", "*.xlsx"));
        fileChooser.setInitialDirectory(new File("C:\\"));
    }

    @ActionProxy(text="Open File")
    public File openFileAction() {
        System.out.println("openFileAction");

        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null && selectedFile.isFile()) {
            if (selectedFile.canWrite()) {
                // notificationPane.setText("File opened:" + selectedFile.getName());
                return selectedFile;
            } else {
                // notificationPane.setText("You do not have permissions to this file");
            }
        } else {
            // notificationPane.setText("File does not exists");
        }
        return null;
    }

    @ActionProxy(text="Close File")
    public void closeFileAction() {
        System.out.println("closeFileAction");
    }

    @ActionProxy(text="Reload File")
    public void reloadFileAction() {
        System.out.println("reloadFileAction");
    }

    private ObservableList<Action> flatten( Collection<? extends Action> actions, ObservableList<Action> dest ) {
        
        for (Action a : actions) {
           if ( a == null || a == ActionUtils.ACTION_SEPARATOR ) continue;
           dest.add(a); 
           if ( a instanceof ActionGroup ) {
               flatten( ((ActionGroup)a).getActions(), dest);
           }
        }
        
        return dest;
    }

    public MenuBar getMenuBar() {
        return ActionUtils.createMenuBar(actions);
    }
}
