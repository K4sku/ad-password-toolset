package pl.tbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pl.tbs.model.Student;
import pl.tbs.model.StudentDataModel;

public enum XlsxHandler {
    INSTANCE;

    private StudentDataModel studentDM;
    private final Student.Year[] yearEnum = Student.Year.values();

    public void initModel(StudentDataModel studentDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;
    }

    public void loadWorkbookFromFile() {
        File selectedFile = studentDM.getSelectedFile();
        FileInputStream fis;
        if (selectedFile == null)
            throw new IllegalStateException("No file selected");
        try {
            fis = new FileInputStream(selectedFile); // obtaining bytes from the file
            studentDM.setWorkbook(new XSSFWorkbook(fis)); // creating Workbook instance that refers to .xlsx file
            studentDM.getWorkbook().setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
            studentDM.setSelectedSheet(studentDM.getWorkbook().getSheetAt(0)); // creating Sheet object to retrieve object
            Iterator<Row> rowIterator = studentDM.getSelectedSheet().rowIterator();
            studentDM.getStudentList().clear();
            while (rowIterator.hasNext()) { // iterating over rows in sheet
                Row row = rowIterator.next();
                if (row.getRowNum() < 0 // skip last row
                        || row.getRowNum() == 0 // skip header row
                        || row.getPhysicalNumberOfCells() == 0 // skip if row is empty
                        || (row.getPhysicalNumberOfCells() >= 1 && row.getFirstCellNum() == 8)) // skip if row only
                                                                                                // contains powershell
                                                                                                // functions
                                continue;
                Student student = new Student();
                student.setRowNumber(row.getRowNum());
                student.setYear(readCellValueAsEnum(row.getCell(0)));
                student.setForm(readCellValueAsString(row.getCell(1)));
                student.setUpn(readCellValueAsString(row.getCell(2)));
                student.setFirstName(readCellValueAsString(row.getCell(3)));
                student.setLastName(readCellValueAsString(row.getCell(4)));
                student.setDisplayName(readCellValueAsString(row.getCell(5)));
                student.setEmail(readCellValueAsString(row.getCell(6)));
                student.setPassword(readCellValueAsString(row.getCell(7)));
                // add student to StudentList if not empty
                if (!student.isStudentEmpty()) {
                    studentDM.getStudentList().add(student);
                }
            }
            studentDM.setWorkbookOpen(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // translates xlsx cell types to String
    private String readCellValueAsString(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell != null) {
            return switch (cell.getCellType()) {
            case _NONE -> null;
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case STRING -> cell.getStringCellValue();
            case FORMULA -> cell.getCellFormula();
            case BLANK -> null;
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case ERROR -> Byte.toString(cell.getErrorCellValue());
            default -> "UNKNOWN value of type " + cell.getCellType();
            };
        }
        return "";
    }

    private Student.Year readCellValueAsEnum(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell != null) {
            return switch (cell.getCellType()) {
            case NUMERIC -> yearEnum[(int) cell.getNumericCellValue() + 2];
            case STRING -> Student.Year
                    .valueOf(cell.getStringCellValue().trim().replace("-", "").replace(" ", "").toUpperCase());
            default -> null;
            };
        }
        return null;
    }

    // update filed in StudentDataModel workbook
    protected void updateStudentInWorkbook(Student student) {
        XSSFSheet sheet = studentDM.getSelectedSheet();
        Row row = sheet.getRow(student.getRowNumber());
        
        if (row == null) {
            row = sheet.createRow(student.getRowNumber());
        }
        row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getYear().toString());
        row.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getForm());
        row.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getUpn());
        row.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getFirstName());
        row.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getLastName());
        row.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getDisplayName());
        row.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getEmail());
        row.getCell(7, MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(student.getPassword());
    }

    public void saveWorkbookToFile() {
        if (studentDM.getWorkbook() == null)
            throw new IllegalStateException("Workbook is not initialized");
        try {
            File selectedFile = studentDM.getSelectedFile();
            OutputStream fileOut = new FileOutputStream(selectedFile);
            studentDM.getWorkbook().write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeWorkbook() throws IOException {
        studentDM.getWorkbook().close();
        studentDM.setWorkbookOpen(false);
        studentDM.getSelectedStudentsSet().clear();
        studentDM.setSelectedStudent(new Student());
        studentDM.getStudentList().clear();
    }
}