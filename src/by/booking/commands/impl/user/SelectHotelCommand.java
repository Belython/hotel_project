package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.commands.factory.CommandType;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.dto.HotelDto;
import by.booking.dto.OrderDto;
import by.booking.entities.Hotel;
import by.booking.entities.Room;
import by.booking.entities.RoomType;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.requestHandler.ServletAction;
import by.booking.services.impl.HotelServiceImpl;
import by.booking.services.impl.RoomServiceImpl;
import by.booking.services.impl.RoomTypeServiceImpl;
import by.booking.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class SelectHotelCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction;
        String page = null;
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(3000);
        try {
            OrderDto order = RequestParameterParser.getOrder(request);
            session.setAttribute(Parameters.ORDER, order);
            // TODO: 26.06.2016 ЭТО ВСЕ ВРЕМЕННО
            roomTypeFill();
            String hotelName = order.getHotel().getName();
            if (!hotelName.equals(Parameters.ANY_HOTEL)) {
                Hotel hotel = HotelServiceImpl.getInstance().getByHotelName(hotelName);
                session.setAttribute(Parameters.HOTEL, hotel);
                servletAction = ServletAction.CALL_COMMAND;
                servletAction.setCommandName(CommandType.SELECTROOM.name());
            } else {
                List<Room> availableRooms = RoomServiceImpl.getInstance().getAvailableRooms(order);
                session.setAttribute(Parameters.HOTEL_HOTELS_LIST, getHotels(availableRooms));
                page = PagePath.CLIENT_SELECT_HOTEL_PATH;
                servletAction = ServletAction.FORWARD_PAGE;
            }
        } catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            servletAction = ServletAction.REDIRECT_PAGE;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }

    private void roomTypeFill() throws ServiceException{
        List<RoomType> roomTypes = RoomTypeServiceImpl.getInstance().getAll();
        List<String > facilities = new ArrayList<>();
        facilities.add("wi-fi");
        facilities.add("safe");
        for (RoomType roomType: roomTypes) {
            roomType.setFacilities(facilities);
            RoomTypeServiceImpl.getInstance().update(roomType);
        }
    }

    private List<HotelDto> getHotels(List<Room> roomList) {
        List<HotelDto> hotelList = new ArrayList<>();
        int separator = 0;
        for (int i = 0; i < roomList.size(); i++) {
            if (i < (roomList.size() - 1)) {
                String curHotelName = roomList.get(i).getHotel().getName();
                String nextHotelName = roomList.get(i + 1).getHotel().getName();
                if (!curHotelName.equals(nextHotelName)) {
                    Hotel hotel = roomList.get(i).getHotel();
                    HotelDto hotelDto = new HotelDto(hotel.getId(), hotel.getCountry(), hotel.getCity(), hotel.getName(),
                            roomList.subList(separator, i + 1));
                    hotelList.add(hotelDto);
                    separator = i + 1;
                }
            } else {
                Hotel hotel = roomList.get(i).getHotel();
                HotelDto hotelDto = new HotelDto(hotel.getId(), hotel.getCountry(), hotel.getCity(), hotel.getName(),
                        roomList.subList(separator, i + 1));
                hotelList.add(hotelDto);
                separator = i + 1;
            }
        }
        return hotelList;
    }

}
