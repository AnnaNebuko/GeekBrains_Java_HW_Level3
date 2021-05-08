package ru.geekbrains.gb;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import ru.geekbrains.gb.server.ChatServer;

public class ServerApp {
    private static final Logger logger =
            Logger.getLogger(ServerApp.class.getName());

    public static Logger getLogger() {
        return logger;
    }

    public static void main(String[] args) {
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
        logger.fatal("Fatal");

        BasicConfigurator.configure();
        logger.info("Entering application.");
        new ChatServer();
    }
}
