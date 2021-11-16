package pl.tbs.controller;

import javafx.collections.ObservableList;
import pl.tbs.model.LogDataModel;
import pl.tbs.model.LogEntry;
import pl.tbs.model.LogLevel;

public enum Logger implements Log {
    INSTANCE;

    private LogDataModel logDM;

    public void initDM(LogDataModel logDM) {
        // ensure model is set once
        if (this.logDM != null) {
            throw new IllegalStateException("StudentDataModel can only be initialized once");
        }

        this.logDM = logDM;
    }

    public void add(LogEntry entry) {
        logDM.gObservableList().add(entry);
    }

    public void add(String message) {
        logDM.gObservableList().add(new LogEntry(message));
    }

    public void add(LogLevel level, String message) {
        logDM.gObservableList().add(new LogEntry(level, message));
    }

    public ObservableList<LogEntry> gObservableList() {
        return logDM.gObservableList();
    }
}
