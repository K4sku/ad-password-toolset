package pl.tbs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import org.controlsfx.control.SegmentedButton;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import pl.tbs.model.Student;
import pl.tbs.model.StudentDataModel;
import pl.tbs.model.Student.Year;

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
    private ObservableSet<ToggleButton> selectedBToggleButtons = FXCollections.observableSet();
    private StudentDataModel studentDM;
    private HashMap<ToggleButton, Year> buttonYearMap = new HashMap<>(15);

    // filter by name
    private ObjectProperty<Predicate<Student>> stringFilter = new SimpleObjectProperty<>();
    // filter by year selection
    private ObjectProperty<Predicate<Student>> selectionFilter = new SimpleObjectProperty<>();

    public void initialize() {
        buttonYearMap.put(ytPreNur, Year.PRENURSERY);
        buttonYearMap.put(ytNur, Year.NURSERY);
        buttonYearMap.put(ytY1, Year.YEAR1);
        buttonYearMap.put(ytY2, Year.YEAR2);
        buttonYearMap.put(ytY3, Year.YEAR3);
        buttonYearMap.put(ytY4, Year.YEAR4);
        buttonYearMap.put(ytY5, Year.YEAR5);
        buttonYearMap.put(ytY6, Year.YEAR6);
        buttonYearMap.put(ytY7, Year.YEAR7);
        buttonYearMap.put(ytY8, Year.YEAR8);
        buttonYearMap.put(ytY9, Year.YEAR9);
        buttonYearMap.put(ytY10, Year.YEAR10);
        buttonYearMap.put(ytY11, Year.YEAR11);
        buttonYearMap.put(ytY12, Year.YEAR12);
        buttonYearMap.put(ytY13, Year.YEAR13);
        yearSelectionList = new ArrayList<>(Arrays.asList(ytPreNur, ytNur, ytY1, ytY2, ytY3, ytY4, ytY5, ytY6, ytY7,
                ytY8, ytY9, ytY10, ytY11, ytY12, ytY13));

        yearSelectionButtons.setToggleGroup(new ToggleGroup());
        yearSelectionButtons.getButtons().addAll(yearSelectionList);
        yearSelectionButtons.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        

        for (ToggleButton tb : yearSelectionList) {
            tb.selectedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    selectedBToggleButtons.add(tb);
                } else {
                    selectedBToggleButtons.remove(tb);
                }
            }
            );
        }
    }

    public void initModel(StudentDataModel studentDM) {
        // ensure model is set once
        if (this.studentDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.studentDM = studentDM;
        setTextFilterPredicateBinding();
        setSelectionFilterPredicateBinding();

        //sets combined filter to studentDM.filteredStudentList
        studentDM.getFilteredStudentList().predicateProperty().bind(Bindings.createObjectBinding(
                () -> stringFilter.get().and(selectionFilter.get()), stringFilter, selectionFilter));

    }

    private void setTextFilterPredicateBinding() {
        stringFilter.bind(Bindings.createObjectBinding(() -> student -> {
            // If filter is empty display all
            if (searchField.getText() == null || searchField.getText().isEmpty()) {
                return true;
            }
            String lowerCaseFilter = searchField.getText().toLowerCase();
            // check if searched text is an email address, search email property if so
            if (lowerCaseFilter.contains("@") || lowerCaseFilter.contains("_")) {
                return student.getEmail().toLowerCase().contains(lowerCaseFilter);
            } else {
                // compare searched text with display name
                return student.getDisplayName().toLowerCase().contains(lowerCaseFilter);
            }
        }, searchField.textProperty()));
    }

    private void setSelectionFilterPredicateBinding() {
        selectionFilter.bind(Bindings.createObjectBinding(() -> 
            student -> {
                if (yearSelectionButtons.getToggleGroup().getSelectedToggle() == null) return true;
                return buttonYearMap.get(yearSelectionButtons.getToggleGroup().getSelectedToggle()) == student.getYear();
            },
            selectedBToggleButtons));
    }


}
