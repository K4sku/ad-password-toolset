package pl.tbs.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.tbs.model.LogEntry;
import pl.tbs.model.LogLevel;
import pl.tbs.model.StudentDataModel;

public class ActionsBarController {
    private LogController loggerC;
    private TableViewController tableViewC;
    private Logger logger = Logger.INSTANCE;

    @FXML
    private Button setPasswordButton;
    @FXML
    private Button randomPasswordButton;
    @FXML
    private TextField passwordField;
    @FXML 
    private Label chosenStudentLabel;

    private final DinoPassAPIClient dinopassAPI = new DinoPassAPIClient();
    private StudentDataModel studentDM;

    public void initialize(){

    }

    public void dInjection(LogController loggerC, TableViewController tableViewC) {
        this.loggerC = loggerC;
        this.tableViewC = tableViewC;
    }

    public void initModel(StudentDataModel studentDM) {
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;

        chosenStudentLabel.textProperty().bind(studentDM.getSelectedStudent().firstNameProperty());
    }
    
    @FXML
    private void onSetPasswordButton(){
        String userIdentity = "";
        String password = passwordField.getText();
        try {
            PowershellResponse response = PowershellAPI.executeCommand("Set-ADAccountPassword -Identity " + userIdentity +" -Server 'NAEWAWWLIDCO01.eu.nordanglia.com' -Reset -NewPassword (ConvertTo-SecureString -AsPlainText "+ password +" -Force)");
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

    @FXML 
    private void onRandomPasswordButton(){
        try {
            passwordField.setText(dinopassAPI.getNewPassword());
        } catch (IOException e) {
            logger.add(new LogEntry(LogLevel.ERROR, e.getMessage()));
        } catch (InterruptedException e){
            logger.add(new LogEntry(LogLevel.ERROR, e.getMessage()));
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void onValidateButton(){
        // TODO
    }

    @FXML
    private void onPrintButton(){
        System.out.println("test");
        // TODO
    }


    

}
