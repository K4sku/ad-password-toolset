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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import pl.tbs.model.Student;

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

    // complete student list
    private ObservableList<Student> studentsList = FXCollections.observableArrayList();
    // which passwords are shown:
    private ObservableSet<Student> studentsWithShownPasswords = FXCollections.observableSet();
    private File selectedFile;
    private XSSFWorkbook workbook;
    private boolean workbookOpen;

    public void initialize() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.setItems(studentsList);
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

            // if the cell is reused for an item from a different row, update it:
            cell.indexProperty().addListener((obs, oldIndex, newIndex) -> updateCell(studentsWithShownPasswords, cell));

            // if the password changes, update:
            cell.itemProperty().addListener((obs, oldItem, newItem) -> updateCell(studentsWithShownPasswords, cell));

            // if the set of students with shown password changes, update the cell:
            studentsWithShownPasswords.addListener((Change<? extends Student> change) -> updateCell(studentsWithShownPasswords, cell));
            return cell;
        });
    }

    private void updateCell(ObservableSet<Student> studentsWithShownPasswords, TableCell<Student, String> cell) {
        int index = cell.getIndex();
        FilteredTableView<Student> table = (FilteredTableView<Student>) cell.getTableView();
        if (index < 0 || index >= table.getItems().size()) {
            cell.setText("");
        } else {
            Student student = table.getItems().get(index);
            if (studentsWithShownPasswords.contains(student)) {
                cell.setText(student.getPassword());
            } else {
                cell.setText(maskPassword(student.getPassword()));
            }
        }
    }

        // masks passwords with asterixs
        private String maskPassword(String text) {
            char[] chars = new char[text.length()];
            Arrays.fill(chars, '*');
            return new String(chars);
        }

    @FXML
    private void tableClickEvent(MouseEvent event) {
        System.out.println("Clicked on: " + tableView.getSelectionModel().getSelectedCells());
        studentsWithShownPasswords.clear();
        if (event.getClickCount() == 2) {
            System.out.println("Double clicked on: " + tableView.getSelectionModel().getSelectedItem());
            studentsWithShownPasswords.add(tableView.getSelectionModel().getSelectedItem());
        }
    }

    void loadWorkbookFromFile(File studentFile) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(studentFile); // obtaining bytes from the file
            workbook = new XSSFWorkbook(fis); // creating Workbook instance that refers to .xlsx file
            workbookOpen = true;
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
            XSSFSheet sheet = workbook.getSheetAt(0); // creating a Sheet object to retrieve object
            Iterator<Row> rowIterator = sheet.rowIterator();
            studentsList.clear();
            while (rowIterator.hasNext()) { // iterating over rows in sheet
                Row row = rowIterator.next();
                if (readCellValueAsString(row.getCell(row.getFirstCellNum())).equals("Year"))
                    continue;
                var student = new Student();
                student.setYear(readCellValueAsString(row.getCell(0)));
                student.setForm(readCellValueAsString(row.getCell(1)));
                student.setUpn(readCellValueAsString(row.getCell(2)));
                student.setFirstName(readCellValueAsString(row.getCell(3)));
                student.setLastName(readCellValueAsString(row.getCell(4)));
                student.setDisplayName(readCellValueAsString(row.getCell(5)));
                student.setEmail(readCellValueAsString(row.getCell(6)));
                student.setPassword(readCellValueAsString(row.getCell(7)));
                studentsList.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // translates xlsx cell types to String
    private String readCellValueAsString(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell != null) {
            return switch (cell.getCellType()) {
                case _NONE -> "<NONE>";
                case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
                case STRING -> cell.getStringCellValue();
                case FORMULA -> cell.getCellFormula();
                case BLANK -> "<BLANK>";
                case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
                case ERROR -> Byte.toString(cell.getErrorCellValue());
                default -> "UNKNOWN value of type " + cell.getCellType();
            };
        }
        return "";
    }

    public boolean isWorkbookOpen() {
        return workbookOpen;
    }

    public void closeWorkbook() throws IOException {
        workbook.close();
        studentsList.clear();
        studentsWithShownPasswords.clear();
    }
    
}
