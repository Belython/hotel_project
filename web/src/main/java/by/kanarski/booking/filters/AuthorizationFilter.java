package by.kanarski.booking.filters;

import by.kanarski.booking.commands.factory.CommandType;
import by.kanarski.booking.constants.MessageConstants;
import by.kanarski.booking.constants.Parameter;
import by.kanarski.booking.entities.User;
import by.kanarski.booking.managers.MessageManager;
import by.kanarski.booking.utils.RequestParameterParser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthorizationFilter implements Filter {
    String page;
    User user;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        CommandType commandType = RequestParameterParser.parseCommandType(httpServletRequest);
        if (commandType.name().equals(commandType.MAKEBILL.toString())) {
//        page = (String) session.getAttribute(Parameter.CURRENT_PAGE_PATH);
//        if ((page != null) && (page.equals(PagePath.CLIENT_SELECT_ROOM_PATH))) {
            user = (User) session.getAttribute(Parameter.USER);
            if (user == null) {
                request.setAttribute(Parameter.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERRON));
//                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(PagePath.INDEX_PAGE_PATH);
//                dispatcher.forward(request, response);
                request.setAttribute(Parameter.COMMAND, "cancelAction");
            }
        }
        next.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
