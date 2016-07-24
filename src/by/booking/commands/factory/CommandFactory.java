package by.booking.commands.factory;

import by.booking.commands.ICommand;
import by.booking.commands.impl.user.LoginUserCommand;
import by.booking.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static CommandFactory instance;

    private CommandFactory(){}

    public static synchronized CommandFactory getInstance(){
        if(instance == null){
            instance = new CommandFactory();
        }
        return instance;
    }

    public ICommand defineCommand(HttpServletRequest request){
        ICommand current = null;
        try{
            CommandType type = RequestParameterParser.parseCommandType(request);
            current = type.getCurrentCommand();
        }
        catch(IllegalArgumentException e){
            current = new LoginUserCommand();
        }
        return current;
    }
}
