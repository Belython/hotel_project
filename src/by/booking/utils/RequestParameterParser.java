package by.booking.utils;

import by.booking.commands.factory.CommandType;
import by.booking.constants.Parameters;
import by.booking.dto.HotelDto;
import by.booking.dto.OrderDto;
import by.booking.entities.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RequestParameterParser {
    private RequestParameterParser() {}

    public static User parseUser(ServletRequest request) {
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

    public static OrderDto getOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Locale currentLocale = (Locale) session.getAttribute(Parameters.LOCALE);
        User user = (User) session.getAttribute(Parameters.USER);
        int totalPersons = Integer.valueOf(request.getParameter(Parameters.ORDER_TOTAL_PERSONS));
        Hotel hotel = parseHotel(request);
        long checkInDate = LocalizationUtil.parseDate(request.getParameter(Parameters.ORDER_CHECK_IN_DATE), currentLocale);
        long checkOutDate = LocalizationUtil.parseDate(request.getParameter(Parameters.ORDER_CHECK_OUT_DATE), currentLocale);
        OrderDto orderDto = new OrderDto(user, hotel, totalPersons, checkInDate, checkOutDate);
        return orderDto;
    }

    public static Hotel parseHotel(ServletRequest request) {
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

    public static String parsePagePath(ServletRequest request) {
        String page = request.getParameter(Parameters.CURRENT_PAGE_PATH);
        return page;
    }

    public static CommandType parseCommandType(HttpServletRequest request){
        String commandName = (String) request.getAttribute(Parameters.COMMAND);
        if (commandName != null) {
            request.removeAttribute(Parameters.COMMAND);
        } else {
            commandName = request.getParameter(Parameters.COMMAND);
        }
        CommandType commandType = CommandType.LOGIN;
        if(commandName != null) {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType;
    }

    public static Locale parseLocale(ServletRequest request) {
        String lang = request.getParameter(Parameters.LOCALE_LANGUAGE);
        Locale locale = new Locale(lang);
        return locale;
    }

    public static Bill parseBill(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        OrderDto orderDto = (OrderDto) session.getAttribute(Parameters.ORDER);
        long checkInDate = orderDto.getCheckInDate();
        long checkOutDate = orderDto.getCheckOutDate();
        HotelDto hotelDto = (HotelDto) session.getAttribute(Parameters.HOTEL_SELECTED_HOTEL);
//        Hotel hotel = hotelDto.getHotel();
        int totalPersons = orderDto.getTotalPersons();
        List<RoomType> roomTypeList = hotelDto.getRoomTypeList();
        HashMap<RoomType, Integer> selectedRoomTypes = new HashMap<>();
        for (RoomType roomType: roomTypeList) {
            int roomTypeCount = Integer.valueOf(request.getParameter(roomType.getName()));
            if (roomTypeCount != 0) {
                selectedRoomTypes.put(roomType, roomTypeCount);
            }
        }
        List<Room> selectedRooms = AdminLogic.chooseRoomList(selectedRoomTypes, hotelDto.getRoomList());
        int paymentAmount = calc(LocalizationUtil.getDays(checkInDate, checkOutDate), selectedRooms);
        Bill bill = EntityBuilder.buildNewBill(user, totalPersons, checkInDate, checkOutDate, selectedRooms,
                paymentAmount);
        return bill;
    }

    private static int calc(int days, List<Room> roomList) {
        int payment = 0;
        for (Room room: roomList) {
            int part = room.getRoomType().getRoomPricePerNight() * days;
            payment += part;
        }
        return payment;
    }


}
