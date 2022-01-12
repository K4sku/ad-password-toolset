package pl.tbs.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
        Dialog<AddStudentDialog.Results> newStudentDialog = AddStudentDialog.instance(settingsDM.getDefaultEmailSuffix());
        Optional<AddStudentDialog.Results> optinalResults = newStudentDialog.showAndWait();
        if (optinalResults.isPresent()) {
            AddStudentDialog.Results results = optinalResults.get();
            // process name to capitalized without whitespaces
            String firstName = StringUtils.trimToEmpty(results.getFirstName());
            firstName = StringUtils.capitalize(firstName.toLowerCase());
            String lastName = StringUtils.trimToEmpty(results.getLastName());
            lastName = StringUtils.capitalize(lastName.toLowerCase());

            String upn = StringUtils.strip(results.getUpn()).toLowerCase();
            String email = String.join("@", upn, StringUtils.strip(results.getDomain().toLowerCase()));

            // add student
            Student student = new Student.Builder()
                    .rowNumber(studentDM.getSelectedSheet().getLastRowNum() + 1)
                    .year(results.getYear())
                    .form(StringUtils.trimToEmpty(results.getForm()))
                    .firstName(firstName)
                    .lastName(lastName)
                    .displayName(String.join(" ", firstName, lastName))
                    .upn(upn)
                    .email(email)
                    .password(StringUtils.trimToEmpty(results.getPassword()))
                    .build();
            studentDM.getStudentList().add(student);
            xlsxHandler.updateStudentInWorkbook(student);
            logger.info("Added student: "+ student.toString());
        }
        
    }

    
    @FXML
    protected void onSettingsMenuItem() {
        logger.trace("settings button");

        FXMLLoader settingsFxmlLoader = new FXMLLoader();
        settingsFxmlLoader.setLocation(getClass().getResource("settingsWindow.fxml"));
        Scene settingsScene;
        try {
            settingsScene = new Scene(settingsFxmlLoader.load());
            Stage settingsStage = new Stage();
            settingsStage.setTitle("Settings");
            settingsStage.setScene(settingsScene);
            settingsStage.initModality(Modality.WINDOW_MODAL);
            settingsStage.initOwner(menuBar.getScene().getWindow());
            settingsStage.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
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
