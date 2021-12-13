package pl.tbs.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.tbs.model.Student.Year;

public class AddStudentDialog {
    public static Dialog<Results> instance() {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("Add student");
        dialog.setHeaderText("Year and upn are required");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ObservableList<Year> years = FXCollections.observableArrayList(Year.values());
        ComboBox<Year> comboBox = new ComboBox<>(years);
        comboBox.getSelectionModel().selectFirst();
        TextField formTField = new TextField("Form");
        TextField upnTField = new TextField("UPN");
        TextField firstNameTField = new TextField("First name");
        TextField lastNameTField = new TextField("Last name");
        TextField passwordTField = new TextField("password");
        dialogPane.setContent(
                new VBox(8, comboBox, formTField, upnTField, firstNameTField, lastNameTField, passwordTField));
        Platform.runLater(formTField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Results(comboBox.getValue(), formTField.getText(), upnTField.getText(),
                        firstNameTField.getText(), lastNameTField.getText(), passwordTField.getText());
            }
            return null;
        });
        return dialog;
    }

    static class Results {
        private Year year;
        private String form;
        private String upn;
        private String firstName;
        private String lastName;
        private String password;

        public Results(Year year, String form, String upn, String firstName, String lastName, String password) {
            this.year = year;
            this.form = form;
            this.upn = upn;
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
