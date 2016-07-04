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
import by.booking.services.impl.RoomServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class SelectRoomCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        try {
            long hotelId = Long.valueOf(request.getParameter(Parameters.HOTEL_SELECTED_HOTEL));
            List<Room> hotelRooms = RoomServiceImpl.getInstance().getByHotelId(hotelId);
            Hotel selectedHotel = hotelRooms.get(0).getHotel();
            HotelDto selectedHotelDto = new HotelDto(hotelId, selectedHotel.getCountry(), selectedHotel.getCity(),
                    selectedHotel.getName(), hotelRooms);
            session.setAttribute(Parameters.HOTEL_SELECTED_HOTEL, selectedHotelDto);
            page = PagePath.CLIENT_SELECT_ROOM_PATH;
        } catch (ServiceException | SQLException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }
}
