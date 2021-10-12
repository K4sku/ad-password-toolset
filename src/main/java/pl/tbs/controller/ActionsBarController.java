package pl.tbs.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pl.tbs.model.LogEntry;
import pl.tbs.model.LogLevel;

public class ActionsBarController {
    private LogController loggerC;
    private TableViewController tableViewC;

    @FXML
    private Button setPasswordButton;
    @FXML
    private Button randomPasswordButton;
    @FXML
    private TextField passwordField;

    private final DinoPassAPIClient dinopassAPI = new DinoPassAPIClient();

    public void initialize(){

    }

    public void dInjection(LogController loggerC, TableViewController tableViewC) {
        this.loggerC = loggerC;
        this.tableViewC = tableViewC;
    }

    @FXML
    private void onSetPasswordButton(){
        String userIdentity = "";
        String password = passwordField.getText();
        try {
            PowershellResponse response = PowershellAPI.executeCommand("Set-ADAccountPassword -Identity " + userIdentity +" -Server 'NAEWAWWLIDCO01.eu.nordanglia.com' -Reset -NewPassword (ConvertTo-SecureString -AsPlainText "+ password +" -Force)");
            if(response.hasError()){
                loggerC.add(new LogEntry(LogLevel.ERROR, response.getErrorAsString()));
            } else if (response.hasOutput()) {
                loggerC.add(new LogEntry(LogLevel.INFO, response.getOutputAsString()));
            } else {
                loggerC.add(new LogEntry("Pasword for "+userIdentity+ "was reset"));
            }
        } catch (IOException e) {
            loggerC.add(new LogEntry(LogLevel.ERROR, e.getLocalizedMessage()));
        }
    }

    @FXML 
    private void onRandomPasswordButton(){
        try {
            passwordField.setText(dinopassAPI.getNewPassword());
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

}
