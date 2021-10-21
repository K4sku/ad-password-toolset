package pl.tbs.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogDataModel {
    private final ObservableList<LogEntry> observableLog = FXCollections.observableArrayList();

    public final ObservableList<LogEntry> gObservableList(){
        return observableLog;
    }

}
