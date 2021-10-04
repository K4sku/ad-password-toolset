package pl.tbs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.SegmentedButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener.Change;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label statusMsg;
    @FXML
    private TextField filePathField;

    @FXML
    private ToggleButton ytPreNur;
    @FXML
    private ToggleButton ytNur;
    @FXML
    private ToggleButton ytY1;
    @FXML
    private ToggleButton ytY2;
    @FXML
    private ToggleButton ytY3;
    @FXML
    private ToggleButton ytY4;
    @FXML
    private ToggleButton ytY5;
    @FXML
    private ToggleButton ytY6;
    @FXML
    private ToggleButton ytY7;
    @FXML
    private ToggleButton ytY8;
    @FXML
    private ToggleButton ytY9;
    @FXML
    private ToggleButton ytY10;
    @FXML
    private ToggleButton ytY11;
    @FXML
    private ToggleButton ytY12;
    @FXML
    private ToggleButton ytY13;
    @FXML
    private SegmentedButton yearSelectionButtons;

    @FXML
    private Button loadFileButton;
    @FXML
    private Button unloadFileButton;

    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> yearColumn;
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

    @FXML
    private void tableClickEvent(MouseEvent event) {
        System.out.println("Clicked on: " + studentTableView.getSelectionModel().getSelectedCells());
        studentsWithShownPasswords.clear();
        if (event.getClickCount() == 2) {
            System.out.println("Double clicked on: " + studentTableView.getSelectionModel().getSelectedItem());
            studentsWithShownPasswords.add(studentTableView.getSelectionModel().getSelectedItem());
        }
    }

    // complete student list
    private ObservableList<Student> studentsList = FXCollections.observableArrayList();
    // which passwords are shown:
    private ObservableSet<Student> studentsWithShownPasswords = FXCollections.observableSet();

    private FileChooser fileChooser;
    private File selectedFile;
    private XSSFWorkbook workbook;
    private ArrayList<ToggleButton> yearSelectionList;

    public void initialize() {
        yearSelectionList = new ArrayList<>(Arrays.asList(ytPreNur, ytNur, ytY1, ytY2, ytY3, ytY4, ytY5, ytY6, ytY7,
                ytY8, ytY9, ytY10, ytY11, ytY12, ytY13));

        yearSelectionButtons.setToggleGroup(null);
        yearSelectionButtons.getButtons().addAll(yearSelectionList);

        studentTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentTableView.setItems(studentsList);
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
            studentsWithShownPasswords
                    .addListener((Change<? extends Student> change) -> updateCell(studentsWithShownPasswords, cell));
            return cell;
        });
    }

    public void initFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    @FXML
    protected void onFileSelectButtonClick() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Spreadsheet", "*.xlsx"));
        fileChooser.setInitialDirectory(new File("C:\\"));
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null && selectedFile.isFile()) {
            if (selectedFile.canWrite()) {
                statusMsg.setText("Loaded file: " + selectedFile.getName());
                filePathField.setText(selectedFile.getAbsolutePath());
            } else {
                filePathField.setText("");
                statusMsg.setText("You do not have permissions to this file");
            }
        } else {
            filePathField.setText("");
            statusMsg.setText("File does not exists");
        }
    }

    @FXML
    protected void onUnloadFileButtonClick() {
        unloadFileButton.setVisible(false);
        if (workbook != null){
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            studentsList.clear();
            studentsWithShownPasswords.clear();
            loadFileButton.setVisible(true);
        }
    }

    @FXML
    protected void onLoadFileButtonClick() {
        selectedFile = new File(filePathField.getText());
        if (selectedFile.isFile()) {
            if (selectedFile.canWrite()) {
                statusMsg.setText("Loading file: " + selectedFile.getName() + " ...");
                loadStudentListFromFile(selectedFile);
                statusMsg.setText("Loaded file: " + selectedFile.getName());
            } else {
                filePathField.setText("");
                statusMsg.setText("You do not have permissions to this file");
            }
        } else {
            filePathField.setText("");
            statusMsg.setText("File does not exists");
        }
    }

    @FXML
    protected void onClearClassesButton() {
        for (ToggleButton tb : yearSelectionList) {
            tb.setSelected(false);
        }
        // studentList.refresh();
    }

    private void loadStudentListFromFile(File studentFile) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(studentFile); // obtaining bytes from the file
            workbook = new XSSFWorkbook(fis); // creating Workbook instance that refers to .xlsx file
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
            XSSFSheet sheet = workbook.getSheetAt(0); // creating a Sheet object to retrieve object
            Iterator<Row> rowIterator = sheet.rowIterator();
            studentsList.clear();
            while (rowIterator.hasNext()) { // iterating over rows in sheet
                Row row = rowIterator.next();
                if (readCellValueAsString(row.getCell(row.getFirstCellNum())).equals("Year"))
                    continue;
                var student = new Student();
                // int startIndex = row.getFirstCellNum();
                // student.setYear(row.getCell(startIndex).getStringCellValue());
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
            unloadFileButton.setVisible(true);
            loadFileButton.setVisible(false);
            // studentTableView.refresh();
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

    private void updateCell(ObservableSet<Student> studentsWithShownPasswords, TableCell<Student, String> cell) {
        int index = cell.getIndex();
        TableView<Student> table = cell.getTableView();
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
}
