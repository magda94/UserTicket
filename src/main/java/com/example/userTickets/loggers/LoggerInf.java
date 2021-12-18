package com.example.userTickets.loggers;

public interface LoggerInf {

    void error(String message);
    void warn(String message);
    void info(String message);
    void info(String message, Object arg);
    void debug(String message);
    void debug(String message, Object arg);
    void debug(String message, Object... args);
    void debug(String message, Throwable throwable);
}
