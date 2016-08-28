package by.kanarski.booking.listeners;

import by.kanarski.booking.constants.PageTextContentName;
import by.kanarski.booking.constants.ResourcePath;
import by.kanarski.booking.constants.SessionAttribute;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Дмитрий on 28.08.2016.
 */
public class TestListener implements ServletRequestAttributeListener {

    @Override
    public void attributeAdded(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        someBlock(servletRequestAttributeEvent);
    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent servletRequestAttributeEvent) {

    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        someBlock(servletRequestAttributeEvent);
    }

    private void someBlock(ServletRequestAttributeEvent servletRequestAttributeEvent) {
        HttpServletRequest sr = (HttpServletRequest) servletRequestAttributeEvent.getServletRequest();
        HttpSession session = sr.getSession();
        Locale locale = (Locale) session.getAttribute(SessionAttribute.LOCALE);
        if (locale == null) {
            locale = Locale.getDefault();
        }
        ResourceBundle bundle = ResourceBundle.getBundle(ResourcePath.TEXT_SOURCE, locale);
        if (!servletRequestAttributeEvent.getName().equals("roomsAmount")) {
            if (!servletRequestAttributeEvent.getName().equals("personsAmount")) {
                for (String element : PageTextContentName.INDEX_PAGE_TEXT_CONTENT) {
                    sr.setAttribute(element, bundle.getString(element));
                }
            }
        }
        System.out.println("govbo");
    }
}
