package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;

import by.booking.requestHandler.ServletAction;
import by.booking.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class SetLocaleCommand implements ICommand{

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        String page;
        HttpSession session = request.getSession();
        //Ищем и задаем локаль пока здесь
        Locale currentLocale = RequestParameterParser.parseLocale(request);
        session.setAttribute(Parameters.LOCALE, currentLocale);
        page = (String) session.getAttribute(Parameters.CURRENT_PAGE_PATH);
        servletAction.setPage(page);
        if (page == null) {
            page = PagePath.INDEX_PAGE_PATH;
            servletAction.setPage(page);
        }
        return servletAction;
    }
}
