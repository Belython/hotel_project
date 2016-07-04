package by.booking.utils;

import org.apache.log4j.Logger;

public class BookingSystemLogger {
    private Logger logger;
    private static BookingSystemLogger instance;

    private BookingSystemLogger(){}

    public static synchronized BookingSystemLogger getInstance(){
        if(instance == null){
            instance = new BookingSystemLogger();
        }
        return instance;
    }

    public void logError(Class sender, String message){
        logger = Logger.getLogger(sender);
        logger.error(message);
    }


}