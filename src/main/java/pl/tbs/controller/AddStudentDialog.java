package pl.tbs.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import pl.tbs.model.Student.Year;

public class AddStudentDialog {
    public static Dialog<Results> instance(String emailDomain) {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("Add student");
        dialog.setHeaderText("Year and upn are required");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ObservableList<Year> years = FXCollections.observableArrayList(Year.values());
        ComboBox<Year> comboBox = new ComboBox<>(years);
        comboBox.getSelectionModel().selectFirst();
        TextField formTField = new TextField();
        formTField.setPromptText("Form / class");
        TextField upnTField = new TextField();
        upnTField.setPromptText("User name");
        upnTField.setMinWidth(190);
        TextField domainTField = new TextField();
        domainTField.setText(emailDomain);
        domainTField.setMaxWidth(120);
        Label atTField = new Label("@");
        TextField firstNameTField = new TextField();
        firstNameTField.setPromptText("First name");
        TextField lastNameTField = new TextField();
        lastNameTField.setPromptText("Last name");
        TextField passwordTField = new TextField();
        passwordTField.setPromptText("Password");

        HBox classHbox = new HBox(8, comboBox, formTField);
        HBox.setHgrow(formTField, Priority.ALWAYS);
        HBox usernameHbox = new HBox(1, upnTField, atTField, domainTField);
        usernameHbox.setAlignment(Pos.CENTER);

        dialogPane.setContent(
                new VBox(8, classHbox, usernameHbox, firstNameTField, lastNameTField, passwordTField));
        Platform.runLater(upnTField::requestFocus);

        final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOk.addEventFilter(ActionEvent.ACTION, event -> {
        if (!validateUpn(upnTField)) {
            event.consume();
     }
 });

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Results(comboBox.getValue(), formTField.getText(), upnTField.getText(), domainTField.getText(),
                        firstNameTField.getText(), lastNameTField.getText(), passwordTField.getText());
            }
            return null;
        });
        return dialog;
    }

    //validate 
    private static boolean validateUpn(TextField upnTField) {
        if (upnTField.getText().trim().isEmpty()) {
            upnTField.requestFocus();
            upnTField.setStyle("-fx-border-color: red");
            if (upnTField.getTooltip() == null) {
                upnTField.setTooltip(new javafx.scene.control.Tooltip("Enter upn"));
            }
            return false;
        }
        return true;
    }

    static class Results {
        private Year year;
        private String form;
        private String upn;
        private String domain;
        private String firstName;
        private String lastName;
        private String password;

        public Results(Year year, String form, String upn, String domain, String firstName, String lastName, String password) {
            this.year = year;
            this.form = form;
            this.upn = upn;
            this.domain = domain;
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
        }

        public Year getYear() {
            return year;
        }

        public String getForm() {
            return form;
        }

        public String getUpn() {
            return upn;
        }

        public String getDomain() {
            return domain;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPassword() {
            return password;
        }

    }

}
