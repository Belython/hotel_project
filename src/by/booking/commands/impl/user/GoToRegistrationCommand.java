package by.booking.commands.impl.user;

import by.booking.commands.ICommand;

import by.booking.constants.PagePath;
import by.booking.constants.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GoToRegistrationCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String page = PagePath.REGISTRATION_PAGE_PATH;
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }
}
