package pl.tbs;

import java.io.File;

import org.controlsfx.control.tableview2.FilteredTableView;
import org.controlsfx.control.tableview2.FilteredTableColumn;

import javafx.fxml.FXML;
import javafx.collections.ObservableList;

public class TableViewController {

    @FXML FilteredTableView<Student> studentTableView;
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

    public ObservableList<Student> loadFileToTable(File file){
        return null;
    }

    public void closeFile(){

    }

    // public void 
    
}
