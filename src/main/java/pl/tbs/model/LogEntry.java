package pl.tbs.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private LogLevel logLevel;
    private String logMsg;
    private final LocalTime logTime;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    public LogEntry(final String logMsg) {
        this.logLevel = LogLevel.INFO;
        this.logMsg = logMsg;
        this.logTime = LocalTime.now();
    }

    public LogEntry(final LogLevel logLevel, final String logMsg) {
    if (logLevel != null) { 
        this.logLevel = logLevel;
     } else {
         this.logLevel = LogLevel.INFO;
    } 
        this.logMsg = logMsg;
        this.logTime = LocalTime.now();
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(final String logMsg) {
        this.logMsg = logMsg;
    }

    public LocalTime getLogTime(){
        return logTime;
    }

    @Override
    public String toString() {
        return "[" + logLevel + "] " + dtf.format(logTime) + " " + logMsg;
    }
}
