package pl.tbs.controller;

import java.io.IOException;

import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.tbs.model.SettingsDataModel;
import pl.tbs.model.StudentDataModel;

public class ActionsBarController {
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
    private SettingsDataModel settingsDM;

    public void initialize() {

    }

    public void initModel(StudentDataModel studentDM, SettingsDataModel settingsDM) {
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;
        this.settingsDM = settingsDM;

        chosenStudentLabel.textProperty().bind(new StringBinding() {
            {
                studentDM.selectedStudentProperty().addListener((obs, oldStudent, newStudent) -> {
                    if (oldStudent != null) {
                        unbind(oldStudent.displayNameProperty());
                    }
                    if (newStudent != null) {
                        bind(newStudent.displayNameProperty());
                    }
                    invalidate();
                });
            }

            @Override
            protected String computeValue() {
                if (studentDM.getSelectedStudent() == null) {
                    return "";
                }
                return studentDM.getSelectedStudent().getDisplayName();
            }
        });

    }

    @FXML
    private void onSetPasswordButton() {
        String userIdentity = studentDM.getSelectedStudent().getUpn();
        String password = passwordField.getText();
        try {
            PowershellResponse response = PowershellHandler.executeCommand("Set-ADAccountPassword -Identity " + userIdentity
                    + " -Server "+ settingsDM.getDomainController() +" -Reset -NewPassword (ConvertTo-SecureString -AsPlainText "
                    + password + " -Force)");
            if (response.hasError()) {
                logger.error(response.getErrorAsString());
            } else if (response.hasOutput()) {
                logger.debug(response.getOutputAsString());
            } else {
                logger.info("Pasword for " + userIdentity + " was set successfully");
                studentDM.getSelectedStudent().setPassword(password);
                XlsxHandler.INSTANCE.updateStudentInWorkbook(studentDM.getSelectedStudent());
                
                SettingsManager.INSTANCE.incrementPasswordResetCount();
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        
    }

    @FXML
    private void onRandomPasswordButton() {
        try {
            passwordField.setText(dinopassAPI.getNewPassword());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @FXML
    private void onValidateButton() {
        // TODO
    }

    @FXML
    private void onPrintButton() {
        if (studentDM.getSelectedStudent() != null) {
            PrinterHandler.INSTANCE.print(studentDM.getSelectedStudent());
            SettingsManager.INSTANCE.incrementPasswordPrintCount();
        }
    }
}
