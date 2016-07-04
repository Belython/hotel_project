package by.booking.utils;

import by.booking.commands.factory.CommandType;
import by.booking.constants.Parameters;
import by.booking.dto.OrderDto;
import by.booking.entities.Hotel;
import by.booking.entities.User;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class RequestParameterParser {
    private RequestParameterParser() {}

    public static User getUser(ServletRequest request) {
        long id = -1;
        if (request.getParameter(Parameters.USER_ID) != null){
            id = Long.valueOf(request.getParameter(Parameters.USER_ID));
        }
        String firstName = request.getParameter(Parameters.USER_FIRST_NAME);
        String lastName = request.getParameter(Parameters.USER_LAST_NAME);
        String email = request.getParameter(Parameters.USER_EMAIL);
        String login = request.getParameter(Parameters.USER_LOGIN);
        String password = request.getParameter(Parameters.USER_PASSWORD);
        String role = request.getParameter(Parameters.USER_ROLE);
        String status = request.getParameter(Parameters.USER_STATUS);
        User user = EntityBuilder.buildUser(id, firstName, lastName, email, login, password, role, status);
        return user;
    }

    public static OrderDto getPreOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale currentLocale = (Locale) session.getAttribute(Parameters.LOCALE);
        User user = (User) session.getAttribute(Parameters.USER);
        int totalPersons = Integer.valueOf(request.getParameter(Parameters.ORDER_TOTAL_PERSONS));
        Hotel hotel = getHotel(request);
        long checkInDate = LocalizationUtil.parseDate(request.getParameter(Parameters.ORDER_CHECK_IN_DATE), currentLocale);
        long checkOutDate = LocalizationUtil.parseDate(request.getParameter(Parameters.ORDER_CHECK_OUT_DATE), currentLocale);
        OrderDto orderDto = new OrderDto(user, hotel, totalPersons, checkInDate, checkOutDate);
        return orderDto;
    }

    public static Hotel getHotel(ServletRequest request) {
        long hotelId = -1;
        if (request.getParameter(Parameters.HOTEL_ID) != null){
            hotelId = Long.valueOf(request.getParameter(Parameters.HOTEL_ID));
        }
        String country = request.getParameter(Parameters.LOCATION_COUNTRY);
        String city = request.getParameter(Parameters.LOCATION_CITY);
        String hotelName = request.getParameter(Parameters.HOTEL_NAME);
        Hotel hotel = EntityBuilder.buildHotel(hotelId, country, city, hotelName, "status");
        return hotel;
    }

    public static String getPagePath(ServletRequest request) {
        String page = request.getParameter(Parameters.CURRENT_PAGE_PATH);
        return page;
    }

    public static CommandType getCommandType(HttpServletRequest request){
        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.LOGIN;
        if(commandName != null) {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType;
    }

    public static Locale getLocale(ServletRequest request) {
        String lang = request.getParameter(Parameters.LOCALE_LANGUAGE);
        Locale locale = new Locale(lang);
        return locale;
    }


}
