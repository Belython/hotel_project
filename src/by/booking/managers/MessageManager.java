package by.booking.managers;

import by.booking.constants.ResourcePath;
import java.util.ResourceBundle;


public class MessageManager {
    private final ResourceBundle bundle = ResourceBundle.getBundle(ResourcePath.MESSAGES_SOURCE);
    private static MessageManager instance;

    private MessageManager(){}

    public static synchronized MessageManager getInstance(){
        if(instance == null){
            instance = new MessageManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return bundle.getString(key);
    }
}
