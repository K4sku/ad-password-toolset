package pl.tbs.model;

import java.io.File;
import java.util.function.Predicate;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

public class StudentDataModel {
        // complete student list
        private final ObservableList<Student> studentList = FXCollections.observableArrayList();
        // Filtered list wrapper
        private final FilteredList<Student> filteredStudentList = new FilteredList<>(studentList, p -> true);
        // Sorted list wrapper
        private final SortedList<Student> sortedStudentsList = new SortedList<>(filteredStudentList);
        // which students are selected
        private final ObservableSet<Student> selectedStudentsSet = FXCollections.observableSet();
        // which student was selected last
        private final ObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>(new Student());
        // xlsx source file
        private final ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();
        // is file opened
        private final BooleanProperty workbookOpen = new SimpleBooleanProperty();
        // opened xlsx file
        private final ObjectProperty<XSSFWorkbook> workbook = new SimpleObjectProperty<>();

        // public void studentDataModel() {

        // }

        public ObservableList<Student> getStudentList() {
            return studentList;
        }

        public FilteredList<Student> getFilteredStudentList() {
            return filteredStudentList;
        }

        public SortedList<Student> getSortedStudentsList() {
            return sortedStudentsList;
        }

        public final Student getSelectedStudent() {
            return selectedStudent.get();
        }

        public final ObjectProperty<Student> selectedStudentProperty() {
            return selectedStudent;
        }

        public final void setSelectedStudent(Student student) {
            selectedStudent.set(student);
        }

        public ObservableSet<Student> getSelectedStudentsSet() {
            return selectedStudentsSet;
        }

        // public ObjectProperty<Predicate<Student>> stringFilter() {
        //     return stringFilter;
        // }

        // public Predicate<Student> getStringFilter() {
        //     return stringFilter.get();
        // }

        // public final void setStringFilter(Predicate<Student> predicate) {
        //     stringFilter.set(predicate);
        // }


        public final File getSelectedFile() {
            return selectedFile.get();
        }

        public final void setSelectedFile(File file) {
            selectedFile.set(file);
        }

        public BooleanProperty workbookOpenProperty(){
            return workbookOpen;
        }

        public final boolean isWorkbookOpen() {
            return workbookOpen.get();
        }

        public final void setWorkbookOpen(boolean arg) {
            workbookOpen.set(arg);
        }

        public ObjectProperty<XSSFWorkbook> workbookProperty() {
            return workbook;
        }

        public final XSSFWorkbook getWorkbook() {
            return workbook.get();
        }

        public final void setWorkbook(XSSFWorkbook wb) {
            workbook.set(wb);
        }

        



}
