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

    public void trace(String msg) {
        logDM.gObservableList().add(new LogEntry(LogLevel.TRACE, msg));
    }

    public void debug(String msg) {
        logDM.gObservableList().add(new LogEntry(LogLevel.DEBUG, msg));
    }

    public void info(String msg) {
        logDM.gObservableList().add(new LogEntry(LogLevel.INFO, msg));
    }

    public void warn(String msg) {
        logDM.gObservableList().add(new LogEntry(LogLevel.WARN, msg));
    }

    public void error(String msg) {
        logDM.gObservableList().add(new LogEntry(LogLevel.ERROR, msg));
    }

    public ObservableList<LogEntry> gObservableList() {
        return logDM.gObservableList();
    }
}
