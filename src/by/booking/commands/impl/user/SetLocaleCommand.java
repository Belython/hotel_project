package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;

import by.booking.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Locale;

public class SetLocaleCommand implements ICommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        //Ищем и задаем локаль пока здесь
        Locale currentLocale = RequestParameterParser.getLocale(request);
        session.setAttribute(Parameters.LOCALE, currentLocale);
        page = (String) session.getAttribute(Parameters.CURRENT_PAGE_PATH);
        if (page == null) {
            page = PagePath.INDEX_PAGE_PATH;
        }
        return page;
    }
}
