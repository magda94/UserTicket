package com.example.userTickets.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectLogger implements LoggerInf {

    private Logger logger;

    private ProjectLogger(String className) {
        this.logger = LoggerFactory.getLogger(className);
    }

    public static ProjectLogger getLogger(String className) {
        return new ProjectLogger(className);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void info(String message, Object arg) {
        logger.info(message, arg);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void debug(String message, Object arg) {
        logger.debug(message, arg);
    }

    @Override
    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        logger.debug(message, throwable);
    }
}
