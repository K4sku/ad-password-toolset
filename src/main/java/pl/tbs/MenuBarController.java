package pl.tbs;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;
    @FXML 
    private MenuItem openFileMenuItem;
    @FXML 
    private MenuItem reloadFileMenuItem;
    @FXML 
    private MenuItem closeFileMenuItem;
    @FXML 
    private MenuItem quitFileMenuItem;
    @FXML
    private Menu exportMenu;
    @FXML
    private Menu toolsMenu;
    @FXML
    private Menu addStudentMenu;

    private LoggerController loggerC;
    private TableViewController tableViewC;
    private FileChooser fileChooser;
    private File selectedFile;
    private boolean isFileOpened = false;

    public void initialize(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Spreadsheet", "*.xlsx"));
        fileChooser.setInitialDirectory(new File("C:\\"));
    }

    public void dInjection(LoggerController loggerC, TableViewController tableViewC) {
        this.loggerC = loggerC;
        this.tableViewC = tableViewC;
    }

    @FXML
    protected void onOpenFileMenuItem() {
        selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (selectedFile != null && selectedFile.isFile()) {
            if (selectedFile.canWrite()) {
                tableViewC.loadWorkbookFromFile(selectedFile);
                loggerC.add(new LogEntry("Loaded file: " + selectedFile.getName()));
                setFileOpenedButtons(true);
            } else {
                loggerC.add(new LogEntry(LogLevel.ERROR, "You do not have permissions to this file"));
            }
        } else {
            loggerC.add(new LogEntry(LogLevel.ERROR, "File does not exists"));
        }
    }

    @FXML
    protected void onCloseFileMenuItem() {
        if (tableViewC.isWorkbookOpen()){
            try {
                tableViewC.closeWorkbook();
                setFileOpenedButtons(false);
            } catch (IOException e) {
                loggerC.add(new LogEntry(LogLevel.WARNING, "File could not be closed"));
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onReloadFileMenuItem(){
        loggerC.add(new LogEntry("Reloaded file: " + selectedFile.getName()));
    }

    @FXML
    protected void onQuitFileMenuItem(){
        Stage stage = (Stage) menuBar.getScene().getWindow();
        onCloseFileMenuItem();
        stage.close();
    }

    @FXML
    protected void onAddStudentMenu(){
        loggerC.add(new LogEntry("add student button"));
    }

    //disables open file button and enables other menu items if file is open.
    //oppsite if file is closed.
    private void setFileOpenedButtons(boolean fileOpened){
            openFileMenuItem.setDisable(fileOpened);
            reloadFileMenuItem.setDisable(!fileOpened);
            closeFileMenuItem.setDisable(!fileOpened);
            exportMenu.setDisable(!fileOpened);
            toolsMenu.setDisable(!fileOpened);
            addStudentMenu.setDisable(!fileOpened);
    }




}
