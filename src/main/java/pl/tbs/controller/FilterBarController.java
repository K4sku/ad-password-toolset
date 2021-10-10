package pl.tbs.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.controlsfx.control.SegmentedButton;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

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

    private ArrayList<ToggleButton> yearSelectionList;
    
    public void initialize() {
        yearSelectionList = new ArrayList<>(Arrays.asList(ytPreNur, ytNur, ytY1, ytY2, ytY3, ytY4, ytY5, ytY6, ytY7,
                ytY8, ytY9, ytY10, ytY11, ytY12, ytY13));

        yearSelectionButtons.setToggleGroup(null);
        yearSelectionButtons.getButtons().addAll(yearSelectionList);
    }

    @FXML
    protected void onClearClassesButton() {
        for (ToggleButton tb : yearSelectionList) {
            tb.setSelected(false);
        }
        // studentList.refresh();
    }

}
