package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
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
import by.booking.services.impl.RoomServiceImpl;
import by.booking.services.impl.RoomTypeServiceImpl;
import by.booking.utils.RequestParameterParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectHotelCommand implements ICommand {

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1000);
        try {
            OrderDto orderDto = RequestParameterParser.getPreOrder(request);
            session.setAttribute(Parameters.ORDER, orderDto);
            // TODO: 26.06.2016 ЭТО ВСЕ ВРЕМЕННО
            roomTypeFill();
            List<Room> availableRooms = RoomServiceImpl.getInstance().getAvailableRooms(orderDto);
            session.setAttribute(Parameters.HOTEL_HOTELS_LIST, getHotels(availableRooms));
            page = PagePath.CLIENT_SELECT_HOTEL_PATH;
        } catch (ServiceException | SQLException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }

    private void roomTypeFill() throws ServiceException, SQLException{
        List<RoomType> roomTypes = RoomTypeServiceImpl.getInstance().getAll();
        List<String > facilities = new ArrayList<>();
        facilities.add("wi-fi");
        facilities.add("safe");
        for (RoomType roomType: roomTypes) {
            roomType.setFacilities(facilities);
            RoomTypeServiceImpl.getInstance().update(roomType);
        }
    }

    private List<HotelDto> getHotels(List<Room> rooms) {
        List<HotelDto> hotelDtos = new ArrayList<>();
        int separator = 0;
        for (int i = 0; i < rooms.size(); i++) {
            if (i < (rooms.size() - 1)) {
                String curHotelName = rooms.get(i).getHotel().getName();
                String nextHotelName = rooms.get(i + 1).getHotel().getName();
                if (!curHotelName.equals(nextHotelName)) {
                    Hotel hotel = rooms.get(i).getHotel();
                    HotelDto hotelDto = new HotelDto(hotel.getId(), hotel.getCountry(), hotel.getCity(), hotel.getName(),
                            rooms.subList(separator, i + 1));
                    hotelDtos.add(hotelDto);
                    separator = i + 1;
                }
            } else {
                Hotel hotel = rooms.get(i).getHotel();
                HotelDto hotelDto = new HotelDto(hotel.getId(), hotel.getCountry(), hotel.getCity(), hotel.getName(),
                        rooms.subList(separator, i + 1));
                hotelDtos.add(hotelDto);
                separator = i + 1;
            }
        }
        return hotelDtos;
    }

}
