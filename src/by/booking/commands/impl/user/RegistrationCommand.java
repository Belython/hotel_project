package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.entities.User;
import by.booking.managers.MessageManager;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.exceptions.ServiceException;
import by.booking.services.impl.UserServiceImpl;
import by.booking.utils.RequestParameterParser;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class RegistrationCommand implements ICommand {
    private User user;

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        try{
            user = RequestParameterParser.getUser(request);
//            accountIdString = request.getParameter(Parameters.ACCOUNT_ID);
            if(areFieldsFullStocked()){
//                account = RequestParameterParser.getAccount(request);
                if(UserServiceImpl.getInstance().checkIsNewUser(user)){
                    UserServiceImpl.getInstance().registrateUser(user);
                    page = PagePath.REGISTRATION_PAGE_PATH;
                    request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.SUCCESS_OPERATION));
                }
                else{
                    page = PagePath.REGISTRATION_PAGE_PATH;
                    request.setAttribute(Parameters.ERROR_USER_EXISTS, MessageManager.getInstance().getProperty(MessageConstants.USER_EXISTS));
                }
            }
            else{
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.EMPTY_FIELDS));
                page = PagePath.REGISTRATION_PAGE_PATH;
            }
        }
        catch (ServiceException | SQLException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        catch (NumberFormatException e) {
            request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.INVALID_NUMBER_FORMAT));
            page = PagePath.REGISTRATION_PAGE_PATH;
        }
        // TODO исправить
        catch(NullPointerException e){
            page = PagePath.INDEX_PAGE_PATH;
            e.printStackTrace();
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }

    // TODO javascript???
    private boolean areFieldsFullStocked(){

        boolean isFullStocked = false;
        if(!user.getFirstName().isEmpty()
                & !user.getLastName().isEmpty()
                & !user.getLogin().isEmpty()
                & !user.getPassword().isEmpty()
                & !user.getEmail().isEmpty()){
            isFullStocked = true;
        }
        return isFullStocked;
    }
}
