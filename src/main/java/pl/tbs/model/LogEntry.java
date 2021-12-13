package pl.tbs.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private LogLevel logLevel;
    private String logMsg;
    private final LocalDateTime logDateTime;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");

    public LogEntry(final LogLevel logLevel, final String logMsg) {
        this.logLevel = logLevel;
        this.logMsg = logMsg;
        this.logDateTime = LocalDateTime.now();
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

    public LocalDateTime getLogTime() {
        return logDateTime;
    }

    @Override
    public String toString() {
        return "[" + logLevel + "] " + dtf.format(logDateTime) + " " + logMsg;
    }
}
