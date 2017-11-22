package com.log.example;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yangzhiguo  2017/10/30.
 */
@Component
public class Demo {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("com.log.example.Demo");
        logger.debug("Hello world");
        LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
        StatusPrinter.print(loggerContext);
    }
}
