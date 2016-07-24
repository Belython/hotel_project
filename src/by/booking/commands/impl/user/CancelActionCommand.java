package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.requestHandler.ServletAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CancelActionCommand implements ICommand{

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        HttpSession session = request.getSession();
        String page = (String) session.getAttribute(Parameters.CURRENT_PAGE_PATH);
        String tsst = request.getParameter(Parameters.COMMAND);
        String tssst = (String) request.getAttribute(Parameters.COMMAND);
        if (page == null) {
            page = PagePath.INDEX_PAGE_PATH;
        }
        servletAction.setPage(page);
        return servletAction;
    }
}
