package pl.tbs.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsWindowController {

    @FXML
    private CheckBox autosaveCBox;

    @FXML
    private CheckBox customCredCBox;

    @FXML
    private TextField domainTField;

    @FXML
    private TextField emailTField;

    @FXML
    private CheckBox openOnStartCBox;

    @FXML
    private PasswordField pwdTField;

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button closeButtonNoSave;

    @FXML
    private TextField usernameTField;

    @FXML
    private Button validateButton;

    @FXML
    private Label closeWarningLabel;

    @FXML
    void onCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onCloseButtonActionNoSave(ActionEvent event) {
        closeWarningLabel.setVisible(true);
        closeButtonNoSave.setVisible(false);
        closeButton.setVisible(true);
        closeButton.setDisable(false);
        Platform.runLater(saveButton::requestFocus);
    }

    @FXML
    void onSaveButtonAction(ActionEvent event) {
        closeWarningLabel.setVisible(false);
        saveButton.setDisable(true);
        closeButton.setVisible(true);
        closeButton.setDisable(false);
        closeButtonNoSave.setVisible(false);
        closeButtonNoSave.setDisable(true);

    }

    public void initialize() {
        // change to any field unlocks save button
        domainTField.textProperty().addListener(observable -> unsavedChangesState());
        emailTField.textProperty().addListener(observable -> unsavedChangesState());
        openOnStartCBox.selectedProperty().addListener(observable -> unsavedChangesState());
        autosaveCBox.selectedProperty().addListener(observable -> unsavedChangesState());
        customCredCBox.selectedProperty().addListener(observable -> unsavedChangesState());
        usernameTField.textProperty().addListener(observable -> unsavedChangesState());
        pwdTField.textProperty().addListener(observable -> unsavedChangesState());

        // cutomCredentialsCheckBox locks/unlocks user and password fields and validate button
        customCredCBox.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                usernameTField.setDisable(!newValue);
                pwdTField.setDisable(!newValue);
                validateButton.setDisable(!newValue);
            }
        );
    }

    private void unsavedChangesState() {
        saveButton.setDisable(false);
        closeButton.setVisible(false);
        closeButton.setDisable(true);
        closeButtonNoSave.setVisible(true);
        closeButtonNoSave.setDisable(false);
    }

}
