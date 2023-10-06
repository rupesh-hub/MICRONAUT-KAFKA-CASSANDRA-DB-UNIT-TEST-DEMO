package com.rupesh.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomLogger {

    private Logger log;

    private CustomLogger(Class<?> clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    public static CustomLogger getInstance(Class<?> clazz) {
        return new CustomLogger(clazz);
    }

    public void log(final String message) {
        log.info(message);
    }

}
