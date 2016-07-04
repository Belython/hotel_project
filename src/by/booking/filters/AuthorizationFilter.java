package by.booking.filters;

import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.entities.User;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.requestHandler.RequestHandler;
import by.booking.services.impl.UserServiceImpl;
import by.booking.utils.RequestParameterParser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class AuthorizationFilter implements Filter{
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
        page = (String) session.getAttribute(Parameters.CURRENT_PAGE_PATH);
        if (page == null) {
            page = PagePath.INDEX_PAGE_PATH;
        }
        if (page.equals(PagePath.CLIENT_SELECT_ROOM_PATH)) {
            user = (User) session.getAttribute(Parameters.USER);
            if (user == null) {
                request.setAttribute(Parameters.OPERATION_MESSAGE, MessageManager.getInstance().getProperty(MessageConstants.AUTHORIZATION_ERRON));
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(page);
                httpServletResponse.sendRedirect(page);
//                dispatcher.forward(request, response);
            }
        }
        next.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
