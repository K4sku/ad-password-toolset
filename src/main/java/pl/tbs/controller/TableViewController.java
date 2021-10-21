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

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import pl.tbs.model.Student;
import pl.tbs.model.StudentDataModel;

public class TableViewController {

    @FXML
    private FilteredTableView<Student> tableView;
    @FXML
    private FilteredTableColumn<Student, String> yearColumn;
    @FXML
    private FilteredTableColumn<Student, String> formColumn;
    @FXML
    private FilteredTableColumn<Student, String> upnColumn;
    @FXML
    private FilteredTableColumn<Student, String> fNameColumn;
    @FXML
    private FilteredTableColumn<Student, String> lNameColumn;
    @FXML
    private FilteredTableColumn<Student, String> emailColumn;
    @FXML
    private FilteredTableColumn<Student, String> passwordColumn;

    private StudentDataModel studentDM;

    // complete student list
    // private ObservableList<Student> studentsList =
    // FXCollections.observableArrayList();
    // which passwords are shown:
    // private ObservableSet<Student> studentsWithShownPasswords =
    // FXCollections.observableSet();
    private File selectedFile;
    private XSSFWorkbook workbook;
    private boolean workbookOpen;

    public void initialize() {

    }

    public void initModel(StudentDataModel studentDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(this.studentDM.getStudentList());
        yearColumn.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        formColumn.setCellValueFactory(cellData -> cellData.getValue().formProperty());
        upnColumn.setCellValueFactory(cellData -> cellData.getValue().upnProperty());
        fNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());

        passwordColumn.setCellFactory(c -> {
            // plain old cell:
            TableCell<Student, String> cell = new TableCell<>();
            updateCell(cell);
            return cell;
        });

        tableView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> studentDM.setSelectedStudent(newSelection));

        tableView.getSelectionModel().getSelectedItems().addListener(multiSelection);

        // ObservableSet<Student> selectedSet = studentDM.getSelectedStudentsSet();

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
