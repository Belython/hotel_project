package by.kanarski.booking.filters;

import by.kanarski.booking.constants.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LocalizationFilter implements Filter {

    private static void mull(HttpServletRequest request) {
        Set<String> set = new HashSet<>();
        set.add("ru");
        set.add("en");
        set.add("de");
        request.setAttribute("langSet", set);
    }

    @Override
    public void init(FilterConfig encodingConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        mull(httpServletRequest);
        HttpSession session = httpServletRequest.getSession();
        Locale locale = (Locale) session.getAttribute(SessionAttribute.LOCALE);
        if (locale == null) {
            locale = request.getLocale();
            session.setAttribute(SessionAttribute.LOCALE, locale);
            request.setAttribute(SessionAttribute.LOCALE, locale.getLanguage());
        }
        next.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
