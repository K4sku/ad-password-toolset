package pl.tbs.model;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class StudentDataModel {
        // complete student list
        private final ObservableList<Student> studentList = FXCollections.observableArrayList();
        // which passwords are shown:
        private final ObservableSet<Student> selectedStudentsSet = FXCollections.observableSet();
        // which student was selected last:
        private final ObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>(new Student());
        // xlsx source file
        private final ObjectProperty<File> selectedFile = new SimpleObjectProperty<>();
        // is file opened
        private final BooleanProperty workbookOpen = new SimpleBooleanProperty();

        public final Student getSelectedStudent() {
            return selectedStudent.get();
        }

        public final void setSelectedStudent(Student student) {
            selectedStudent.set(student);
        }

        public ObservableSet<Student> getSelectedStudentsSet() {
            return selectedStudentsSet;
        }

        public ObservableList<Student> getStudentList() {
            return studentList;
        }

        public final File getSelectedFile() {
            return selectedFile.get();
        }

        public final void setSelectedFile(File file) {
            selectedFile.set(file);
        }

        public BooleanProperty workbookOpen(){
            return workbookOpen;
        }

        public final boolean isWorkbookOpen() {
            return workbookOpen.get();
        }

        public final void setWorkbookOpen(boolean arg) {
            workbookOpen.set(arg);
        }



}