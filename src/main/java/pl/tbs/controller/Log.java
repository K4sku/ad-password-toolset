package pl.tbs.controller;

import pl.tbs.model.LogEntry;

public interface Log {
    public void trace(String msg);
    public void debug(String msg);
    public void info(String msg);
    public void warn(String msg);
    public void error(String msg);
}
