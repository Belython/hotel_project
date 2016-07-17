package by.booking.commands.impl.user;

import by.booking.commands.ICommand;
import by.booking.constants.MessageConstants;
import by.booking.constants.PagePath;
import by.booking.constants.Parameters;
import by.booking.constants.Statuses;
import by.booking.dto.HotelDto;
import by.booking.dto.OrderDto;
import by.booking.entities.*;
import by.booking.exceptions.ServiceException;
import by.booking.managers.MessageManager;
import by.booking.services.impl.BillServiceImpl;
import by.booking.services.impl.OrderServiceImpl;
import by.booking.services.impl.RoomServiceImpl;
import by.booking.utils.EntityBuilder;
import by.booking.utils.LocalizationUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class MakeOrderCommand implements ICommand{

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        HttpSession session = request.getSession();
        try {
            Order order = createNewOrder(request);
            OrderServiceImpl.getInstance().add(order);
            makeBills();
            page = PagePath.INDEX_PAGE_PATH;
        }
        catch (ServiceException | SQLException e) {
            page = PagePath.ERROR_PAGE_PATH;
            request.setAttribute(Parameters.ERROR_DATABASE, MessageManager.getInstance().getProperty(MessageConstants.ERROR_DATABASE));
        }
        session.setAttribute(Parameters.CURRENT_PAGE_PATH, page);
        return page;
    }

    private Order createNewOrder(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Parameters.USER);
        OrderDto orderDto = (OrderDto) session.getAttribute(Parameters.ORDER);
        long checkInDate = orderDto.getCheckInDate();
        long checkOutDate = orderDto.getCheckOutDate();
        HotelDto hotelDto = (HotelDto) session.getAttribute(Parameters.HOTEL_SELECTED_HOTEL);
        Hotel hotel = hotelDto.getHotel();
        int totalPersons = orderDto.getTotalPersons();
        long roomTypeId = Long.valueOf(request.getParameter(Parameters.ROOM_TYPE_ID));
        RoomType roomType = hotelDto.getRoomTypeById(roomTypeId);
        Order order = EntityBuilder.buildOrder(user, totalPersons, roomType, hotel, checkInDate, checkOutDate);
        return order;
    }

    private void makeBills() throws ServiceException, SQLException {
        List<Order> orders = OrderServiceImpl.getInstance().getByStatus(Statuses.ORDER_NEW);
        for (Order order: orders) {
            long checkInDate = order.getCheckInDate();
            long checkOutDate = order.getCheckOutDate();
            int pricePerNight = order.getRoomType().getRoomPricePerNight();
            int bookingDays = LocalizationUtil.getDays(checkInDate, checkOutDate);
            int paymentAmount = bookingDays * pricePerNight;
            List<Room> rooms = RoomServiceImpl.getInstance().getByBill(order);
            // TODO: 28.06.2016 придумать логику выбора конкретного номера
            Room room = rooms.get(0);
            Bill bill = EntityBuilder.buildNewBill(paymentAmount, order, room);
            BillServiceImpl.getInstance().add(bill);
            order.setStatus(Statuses.ORDER_CHECKED);
            OrderServiceImpl.getInstance().update(order);
        }
    }

}
