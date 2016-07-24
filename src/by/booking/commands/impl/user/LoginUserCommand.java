package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.managers.MessageManager;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.entities.User;
import by.booking.exceptions.ServiceException;
import by.booking.requestHandler.ServletAction;
import by.booking.services.impl.UserServiceImpl;
import by.booking.utils.RequestParameterParser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginUserCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        String page = null;
        HttpSession session = request.getSession();
        try {
            User user = RequestParameterParser.parseUser(request);
            if (UserServiceImpl.getInstance().checkUserAuthorization(user.getLogin(), user.getPassword())){
                user = UserServiceImpl.getInstance().getUserByLogin(user.getLogin());
                session.setAttribute(Parameters.USER, user);
                page = RequestParameterParser.parsePagePath(request);
                if (page == null) {
                    page = PagePath.INDEX_PAGE_PATH;
                }
            }
            else {
                page = PagePath.INDEX_PAGE_PATH;
                request.setAttribute(Parameters.ERROR_LOGIN_OR_PASSWORD, MessageManager.getInstance().getProperty(MessageConstants.WRONG_LOGIN_OR_PASSWORD));
            }
            session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        }
        catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }

}
