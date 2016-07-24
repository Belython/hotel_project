package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.dto.HotelDto;
import by.booking.entities.Hotel;
import by.booking.entities.Room;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.requestHandler.ServletAction;
import by.booking.services.impl.RoomServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SelectRoomCommand implements ICommand {

    @Override
    public ServletAction execute(HttpServletRequest request, HttpServletResponse response) {
        ServletAction servletAction = ServletAction.FORWARD_PAGE;
        String page;
        HttpSession session = request.getSession();
        try {
            Hotel hotel = (Hotel) session.getAttribute(Parameters.HOTEL);
            List<Room> hotelRooms;
            if (hotel == null) {
                long hotelId = Long.valueOf(request.getParameter(Parameters.HOTEL_SELECTED_HOTEL));
                hotelRooms = RoomServiceImpl.getInstance().getByHotelId(hotelId);
                hotel = hotelRooms.get(0).getHotel();
            } else {
                hotelRooms = RoomServiceImpl.getInstance().getByHotelId(hotel.getId());
            }

            HotelDto selectedHotelDto = new HotelDto(hotel.getId(), hotel.getCountry(), hotel.getCity(),
                    hotel.getName(), hotelRooms);
            session.setAttribute(Parameters.HOTEL_SELECTED_HOTEL, selectedHotelDto);
            page = PagePath.CLIENT_SELECT_ROOM_PATH;
        } catch (ServiceException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        servletAction.setPage(page);
        return servletAction;
    }
}
