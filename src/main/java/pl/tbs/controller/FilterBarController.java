package pl.tbs.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.controlsfx.control.SegmentedButton;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import pl.tbs.model.StudentDataModel;

public class FilterBarController {
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
    private TextField searchField;

    private ArrayList<ToggleButton> yearSelectionList;
    private StudentDataModel studentDM;
    
    public void initialize() {
        yearSelectionList = new ArrayList<>(Arrays.asList(ytPreNur, ytNur, ytY1, ytY2, ytY3, ytY4, ytY5, ytY6, ytY7,
                ytY8, ytY9, ytY10, ytY11, ytY12, ytY13));

        yearSelectionButtons.setToggleGroup(null);
        yearSelectionButtons.getButtons().addAll(yearSelectionList);
    }

    public void initModel(StudentDataModel studentDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;
        setFilterPredicateListener();


    }

    private void setFilterPredicateListener() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            studentDM.getFilteredStudentList().setPredicate(student -> {
                //If filter is empty display all
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newVal.toLowerCase();
                //check if searched text is an email address, search email property if so
                if (newVal.contains("@") || newVal.contains("_")) {
                    return student.getEmail().toLowerCase().contains(lowerCaseFilter);
                } else {
                    //compare searched text with display name
                    return student.getDisplayName().toLowerCase().contains(lowerCaseFilter);
                }
                
            });
        });
    }

    @FXML
    protected void onClearClassesButton() {
        for (ToggleButton tb : yearSelectionList) {
            tb.setSelected(false);
        }
        // studentList.refresh();
    }

}
