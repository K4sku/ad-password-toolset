package pl.tbs.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pl.tbs.model.Student;

public class XlsxLoader {
    void loadWorkbookFromFile(File studentFile) {
        FileInputStream fis;
        try {
            fis = new FileInputStream(studentFile); // obtaining bytes from the file
            workbook = new XSSFWorkbook(fis); // creating Workbook instance that refers to .xlsx file
            workbookOpen = true;
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_NULL_AND_BLANK);
            XSSFSheet sheet = workbook.getSheetAt(0); // creating a Sheet object to retrieve object
            Iterator<Row> rowIterator = sheet.rowIterator();
            // studentsList.clear();
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
                // studentsList.add(student);
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
        // studentsList.clear();
        // studentsWithShownPasswords.clear();
    }
}