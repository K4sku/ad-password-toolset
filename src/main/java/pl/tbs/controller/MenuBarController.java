package pl.tbs.controller;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.tbs.model.LogEntry;
import pl.tbs.model.LogLevel;
import pl.tbs.model.StudentDataModel;

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

    private LogController loggerC;
    private TableViewController tableViewC;
    private FileChooser fileChooser;
    private File selectedFile;
    private String initialDirectory = "C:\\";
    private StudentDataModel studentDM;
    private Logger logger = Logger.INSTANCE;


    public void initialize(){
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Spreadsheet", "*.xlsx"));
        fileChooser.setInitialDirectory(new File(initialDirectory));
    }

    public void dInjection(LogController loggerC, TableViewController tableViewC) {
        this.loggerC = loggerC;
        this.tableViewC = tableViewC;
    }

    public void initModel(StudentDataModel studentDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;
    }

    @FXML
    protected void onOpenFileMenuItem() {
        selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (selectedFile != null && selectedFile.isFile()) {
            if (selectedFile.canWrite()) {
                studentDM.setSelectedFile(selectedFile);
                XlsxLoader.INSTANCE.loadWorkbookFromFile();
                logger.add(new LogEntry("Loaded file: " + selectedFile.getName()));
                setFileOpenedButtons(true);
            } else {
                logger.add(new LogEntry(LogLevel.ERROR, "You do not have permissions to this file"));
            }
        } else {
            logger.add(new LogEntry(LogLevel.ERROR, "File does not exists"));
        }
    }

    @FXML
    protected void onCloseFileMenuItem() {
        if (studentDM.isWorkbookOpen()){
            try {
                XlsxLoader.INSTANCE.closeWorkbook();
                setFileOpenedButtons(false);
            } catch (IOException e) {
                logger.add(new LogEntry(LogLevel.WARNING, "File could not be closed"));
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onReloadFileMenuItem(){
        logger.add(new LogEntry("Reloaded file: " + selectedFile.getName()));
    }

    @FXML
    protected void onQuitFileMenuItem(){
        Stage stage = (Stage) menuBar.getScene().getWindow();
        onCloseFileMenuItem();
        stage.close();
    }

    @FXML
    protected void onAddStudentMenu(){
        logger.add(new LogEntry("add student button"));
    }

    @FXML
    protected void onAbout(){
        String userIdentity = "";
        String password ="";
        try {
            // PowershellAPI.executeCommand("Set-ADAccountPassword -Identity " + userIdentity +" -Server 'NAEWAWWLIDCO01.eu.nordanglia.com' -Reset -NewPassword (ConvertTo-SecureString -AsPlainText "+ password +" -Force)");
            PowershellResponse response = PowershellAPI.executeCommand("[System.Security.Principal.WindowsIdentity]::GetCurrent()");
            if(response.hasError()){
                logger.add(new LogEntry(LogLevel.ERROR, response.getErrorAsString()));
            } else if (response.hasOutput()) {
                logger.add(new LogEntry(LogLevel.INFO, response.getOutputAsString()));
            } else {
                logger.add(new LogEntry("Pasword for "+userIdentity+ "was reset"));
            }
        } catch (IOException e) {
            logger.add(new LogEntry(LogLevel.ERROR, e.getLocalizedMessage()));
        }
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
