package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutUserCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Parameters.USER);
        String  page = request.getParameter(Parameters.CURRENT_PAGE_PATH);
        if (page == null) {
            page = PagePath.INDEX_PAGE_PATH;
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }
}
