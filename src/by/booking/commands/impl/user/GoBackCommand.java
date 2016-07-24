package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.requestHandler.ServletAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoBackCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        HttpSession session = request.getSession();

        String page = (String) session.getAttribute(Parameters.CURRENT_PAGE_PATH);
        if (page == null) {
            page = PagePath.INDEX_PAGE_PATH;
            session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        }
        servletAction.setPage(page);
        return servletAction;
    }
}
