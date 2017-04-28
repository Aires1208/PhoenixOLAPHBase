package com.zte.ums.esight.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
public class XLogger {
    private static Logger logger = LoggerFactory.getLogger(XLogger.class);

    private static XLogger xLogger = new XLogger();

    private AtomicBoolean debug = new AtomicBoolean(false) ;

    public static XLogger getInstance() {
        return xLogger;
    }

    public void log(String input) {
        if(debug.get()) {
            logger.info(input);
        }
    }

    public void error(String input,Throwable t) {
        logger.error(input,t);
    }

    public void switchDebug() {
        debug.set(!debug.get());
    }

    public boolean getDebug() {
        return debug.get();
    }

}
