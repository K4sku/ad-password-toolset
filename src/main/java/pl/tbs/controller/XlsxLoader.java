package pl.tbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pl.tbs.model.Student;
import pl.tbs.model.StudentDataModel;

public enum XlsxLoader {
    INSTANCE;

    private StudentDataModel studentDM;

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
        if (selectedFile == null) return;
        try {
            fis = new FileInputStream(selectedFile); // obtaining bytes from the file
            studentDM.setWorkbook(new XSSFWorkbook(fis)); // creating Workbook instance that refers to .xlsx file
            studentDM.setWorkbookOpen(true);
            studentDM.getWorkbook().setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
            XSSFSheet sheet = studentDM.getWorkbook().getSheetAt(0); // creating a Sheet object to retrieve object
            Iterator<Row> rowIterator = sheet.rowIterator();
            studentDM.getStudentList().clear();
            while (rowIterator.hasNext()) { // iterating over rows in sheet
                Row row = rowIterator.next();
                if (readCellValueAsString(row.getCell(row.getFirstCellNum())).equals("Year"))
                    continue;
                Student student = new Student();
                student.setYear(readCellValueAsString(row.getCell(0)));
                student.setForm(readCellValueAsString(row.getCell(1)));
                student.setUpn(readCellValueAsString(row.getCell(2)));
                student.setFirstName(readCellValueAsString(row.getCell(3)));
                student.setLastName(readCellValueAsString(row.getCell(4)));
                student.setDisplayName(readCellValueAsString(row.getCell(5)));
                student.setEmail(readCellValueAsString(row.getCell(6)));
                student.setPassword(readCellValueAsString(row.getCell(7)));
                studentDM.getStudentList().add(student);
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


    public void closeWorkbook() throws IOException {
        studentDM.getWorkbook().close();
        studentDM.setWorkbookOpen(false);
        studentDM.getSelectedStudentsSet().clear();
        studentDM.setSelectedStudent(new Student());
        studentDM.getStudentList().clear();
    }
}