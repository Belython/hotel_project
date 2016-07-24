package by.booking.services.impl;


import by.booking.constants.Messages;
import by.booking.dao.impl.RoomDao;
import by.booking.dto.OrderDto;
import by.booking.entities.Bill;
import by.booking.entities.Room;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IRoomService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;
import by.booking.utils.ExceptionHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomServiceImpl implements IRoomService{
    private static RoomServiceImpl instance;

    private RoomServiceImpl(){
    }

    public static synchronized RoomServiceImpl getInstance(){
        if(instance == null){
            instance = new RoomServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(Room entity) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Room> getAll() throws ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<Room> rooms = null;
        try {
            connection.setAutoCommit(false);
            rooms = RoomDao.getInstance().getAll();
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
        return rooms;
    }

    @Override
    public Room getById(long id) throws ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        Room room = null;
        try {
            connection.setAutoCommit(false);
            room = RoomDao.getInstance().getById(id);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
        return room;
    }

    @Override
    public void update(Room room) throws ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            RoomDao.getInstance().update(room);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
    }

    @Override
    public void delete(long id) throws ServiceException {

    }

    public List<Room> getAvailableRooms(OrderDto orderDto) throws ServiceException{
        Connection connection = ConnectionUtil.getConnection();
        List<Room> rooms = null;
        try {
            connection.setAutoCommit(false);
            rooms = RoomDao.getInstance().getAvailableRooms(orderDto);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
        return rooms;
    }

    public List<Room> getByHotelId(long hotelId) throws ServiceException{
        Connection connection = ConnectionUtil.getConnection();
        List<Room> rooms = null;
        try {
            connection.setAutoCommit(false);
            rooms = RoomDao.getInstance().getByHotelId(hotelId);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
        return rooms;
    }

    public List<Room> getByBill(Bill bill) throws ServiceException{
        Connection connection = ConnectionUtil.getConnection();
        List<Room> roomList = null;
        try {
            connection.setAutoCommit(false);
            roomList = RoomDao.getInstance().getByIdList(bill.getRoomIdList());
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
        return roomList;
    }

}
