package pl.tbs.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.tbs.model.SettingsDataModel;
import pl.tbs.model.Student;
import pl.tbs.model.StudentDataModel;

public class MenuBarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem openFileMenuItem;
    @FXML
    private MenuItem reloadFileMenuItem;
    @FXML
    private MenuItem saveFileMenuItem;
    @FXML
    private MenuItem closeFileMenuItem;
    @FXML
    private MenuItem autoSaveFileMenuItem;
    @FXML
    private MenuItem quitFileMenuItem;
    @FXML
    private Menu exportMenu;
    @FXML
    private Menu toolsMenu;
    @FXML
    private MenuItem addStudentMenuItem;
    @FXML
    private MenuItem settingsMenuItem;

    private FileChooser fileChooser;
    private File selectedFile;
    private String initialDirectory = "C:\\";
    private StudentDataModel studentDM;
    private SettingsDataModel settingsDM;
    private Logger logger;
    private XlsxHandler xlsxHandler;

    public void initialize() {
        logger = Logger.INSTANCE;
        xlsxHandler = XlsxHandler.INSTANCE;

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Spreadsheet", "*.xlsx"));
        fileChooser.setInitialDirectory(new File(initialDirectory));
    }


    public void initModel(StudentDataModel studentDM, SettingsDataModel settingsDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;
        this.settingsDM = settingsDM;
    }

    @FXML
    protected void onOpenFileMenuItem() {
        selectedFile = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (selectedFile != null && selectedFile.isFile()) {
            if (selectedFile.canWrite()) {
                studentDM.setSelectedFile(selectedFile);
                xlsxHandler.loadWorkbookFromFile();
                logger.info("Loaded file: " + selectedFile.getName());
                setFileOpenedButtons(true);
            } else {
                logger.error("You do not have permissions to this file");
            }
        } else {
            logger.error("File does not exists");
        }
    }

    @FXML
    protected void onSaveFileMenuItem() {
        if (selectedFile != null) {
            xlsxHandler.saveWorkbookToFile();
            logger.info("Saved file: " + selectedFile.getName());
        } else {
            logger.error("No file selected");
        }
    }

    @FXML
    protected void onCloseFileMenuItem() {
        if (studentDM.isWorkbookOpen()) {
            try {
                xlsxHandler.closeWorkbook();
                setFileOpenedButtons(false);
            } catch (IOException e) {
                logger.warn("File could not be closed");
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void onReloadFileMenuItem() {
        logger.info("Reloaded file: " + selectedFile.getName());
    }

    @FXML
    protected void onAutoSaveFileMenuItem() {
        // logger.add("Auto-saved file: " + selectedFile.getName());
    }

    @FXML
    protected void onQuitFileMenuItem() {
        Stage stage = (Stage) menuBar.getScene().getWindow();
        onCloseFileMenuItem();
        stage.close();
    }

    @FXML
    protected void onAddStudentMenuItem() {
        logger.trace("add student button");
        Dialog<AddStudentDialog.Results> newStudentDialog = AddStudentDialog.instance();
        Optional<AddStudentDialog.Results> optinalResults = newStudentDialog.showAndWait();
        if (optinalResults.isPresent()) {
            AddStudentDialog.Results results = optinalResults.get();
            Student student = new Student.Builder()
                    .rowNumber(studentDM.getSelectedSheet().getLastRowNum() + 1)
                    .year(results.getYear())
                    .firstName(results.getFirstName())
                    .lastName(results.getLastName())
                    .displayName(results.getFirstName()+" "+results.getLastName())
                    .upn(results.getUpn())
                    .email(results.getUpn()+settingsDM.getDefaultEmailSuffix())
                    .password(results.getPassword())
                    .build();
            studentDM.getStudentList().add(student);
            xlsxHandler.updateStudentInWorkbook(student);
        }
        
    }

    
    @FXML
    protected void onSettingsMenuItem() {
        logger.trace("settings button");
        
    }

    @FXML
    protected void onAbout() {
        logger.trace("about button");
    }

    // disables open file button and enables other menu items if file is open.
    // oppsite if file is closed.
    private void setFileOpenedButtons(boolean fileOpened) {
        openFileMenuItem.setDisable(fileOpened);
        reloadFileMenuItem.setDisable(!fileOpened);
        saveFileMenuItem.setDisable(!fileOpened);
        closeFileMenuItem.setDisable(!fileOpened);
        exportMenu.setDisable(!fileOpened);
        addStudentMenuItem.setDisable(!fileOpened);
    }

}
