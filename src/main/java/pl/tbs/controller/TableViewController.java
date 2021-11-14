package pl.tbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.tableview2.FilteredTableColumn;
import org.controlsfx.control.tableview2.FilteredTableView;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.controlsfx.control.tableview2.filter.popupfilter.PopupFilter;
import org.controlsfx.control.tableview2.filter.popupfilter.PopupStringFilter;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import pl.tbs.model.Student;
import pl.tbs.model.StudentDataModel;

public class TableViewController {

    @FXML
    private TableView<Student> tableView;
    @FXML
    private TableColumn<Student, Student.Year> yearColumn;
    @FXML
    private TableColumn<Student, String> formColumn;
    @FXML
    private TableColumn<Student, String> upnColumn;
    @FXML
    private TableColumn<Student, String> fNameColumn;
    @FXML
    private TableColumn<Student, String> lNameColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> passwordColumn;

    private StudentDataModel studentDM;

    // complete student list
    // private ObservableList<Student> studentsList =
    // FXCollections.observableArrayList();
    // which passwords are shown:
    // private ObservableSet<Student> studentsWithShownPasswords =
    // FXCollections.observableSet();

    public void initialize() {

    }

    public void initModel(StudentDataModel studentDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;


        //https://code.makery.ch/blog/javafx-8-tableview-sorting-filtering/
        //set placeplaceholder and sorting http://tutorials.jenkov.com/javafx/tableview.html 
        //disable soring by row
        //filtering https://stackoverflow.com/questions/17017364/fast-filtering-in-javafx-tableview
        tableView.setPlaceholder(new Label("Load excel file to display data"));
        tableView.getSortOrder().addAll(yearColumn, formColumn, fNameColumn, lNameColumn);

        tableView.setOnSort(event -> event.consume());

        studentDM.getSortedStudentsList().comparatorProperty().bind(tableView.comparatorProperty());

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(this.studentDM.getSortedStudentsList());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        formColumn.setCellValueFactory(cellData -> cellData.getValue().formProperty());
        upnColumn.setCellValueFactory(cellData -> cellData.getValue().upnProperty());
        fNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        yearColumn.setCellFactory(c -> new TableCell<Student, Student.Year>() {
            @Override
            protected void updateItem(Student.Year year, boolean empty) {
                super.updateItem(year, empty);
                if (year == null) {
                    setText("");
                } else {
                    // setText(Student.Year.getDisplayText(this.getTableRow().getItem().yearProperty().get()));
                    setText(Student.Year.getDisplayText(year));
                }
            }
        }
        );

        passwordColumn.setCellFactory(c -> new TableCell<Student, String>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                if (password == null || password.isEmpty()) {
                    setText("");
                } else {
                    setText(maskPassword(password));
                }
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                studentDM.setSelectedStudent(newSelection);
                System.out.println("selection: " + oldSelection + " -> " + newSelection);
                System.out.println("studentDM.selectedStudent: " + studentDM.getSelectedStudent().getDisplayName());
            }
        });

        tableView.getSelectionModel().getSelectedItems().addListener(multiSelection);

        ObservableSet<Student> selectedSet = studentDM.getSelectedStudentsSet();

    }

    private void updateCell(TableCell<Student, String> cell) {
        int index = cell.getIndex();
        FilteredTableView<Student> table = (FilteredTableView<Student>) cell.getTableView();
        if (index < 0 || index >= table.getItems().size()) {
            cell.setText("");
        } else {
            Student student = table.getItems().get(index);
            cell.setText(maskPassword(student.getPassword()));
        }
    }

    ListChangeListener<Student> multiSelection = new ListChangeListener<Student>() {

        @Override
        public void onChanged(Change<? extends Student> change) {
            while (change.next()) {
                if (change.wasUpdated()) {
                    // update item
                } else {
                    studentDM.getSelectedStudentsSet().removeAll(change.getRemoved());
                    studentDM.getSelectedStudentsSet().addAll(change.getAddedSubList());
                    System.out.println("studentDM.selectedStudentsSet: " + studentDM.getSelectedStudentsSet().toString());
                }
            }
        }
    };

    // masks passwords with asterixs
    private String maskPassword(String text) {
        char[] chars = new char[text.length()];
        Arrays.fill(chars, '*');
        return new String(chars);
    }

    @FXML
    private void tableClickEvent(MouseEvent event) {
        // System.out.println("Clicked on: " +
        // tableView.getSelectionModel().getSelectedCells());
        // studentDM.getSelectedStudentsSet().clear();
        // if (event.getClickCount() == 2) {
        // System.out.println("Double clicked on: " +
        // tableView.getSelectionModel().getSelectedItem());
        // studentDM.getSelectedStudentsSet().add(tableView.getSelectionModel().getSelectedItem());
        // }
    }

}
