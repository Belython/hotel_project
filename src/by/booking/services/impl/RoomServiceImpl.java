package by.booking.services.impl;


import by.booking.constants.Messages;
import by.booking.dao.impl.BillDao;
import by.booking.dao.impl.RoomDao;
import by.booking.dto.OrderDto;
import by.booking.entities.Bill;
import by.booking.entities.Room;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IRoomService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;
import by.booking.utils.EntityBuilder;

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
    public void add(Room entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Room> getAll() throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<Room> rooms = null;
        try {
            connection.setAutoCommit(false);
            rooms = RoomDao.getInstance().getAll();
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
                BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
                throw new ServiceException(e.getMessage());
            } catch (SQLException e1) {
                BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
                throw new ServiceException(e1.getMessage());
            }
        }
        return rooms;
    }

    @Override
    public Room getById(long id) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        Room room = null;
        try {
            connection.setAutoCommit(false);
            room = RoomDao.getInstance().getById(id);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
                BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
                throw new ServiceException(e.getMessage());
            } catch (SQLException e1) {
                BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
                throw new ServiceException(e1.getMessage());
            }
        }
        return room;
    }

    @Override
    public void update(Room room) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            RoomDao.getInstance().update(room);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
                BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
                throw new ServiceException(e.getMessage());
            } catch (SQLException e1) {
                BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
                throw new ServiceException(e1.getMessage());
            }
        }
    }

    @Override
    public void delete(long id) throws SQLException, ServiceException {

    }

    public List<Room> getAvailableRooms(OrderDto orderDto) throws SQLException, ServiceException{
        Connection connection = ConnectionUtil.getConnection();
        List<Room> rooms = null;
        try {
            connection.setAutoCommit(false);
            rooms = RoomDao.getInstance().getAvailableRooms(orderDto);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
                BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
                throw new ServiceException(e.getMessage());
            } catch (SQLException e1) {
                BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
                throw new ServiceException(e1.getMessage());
            }
        }
        return rooms;
    }

    public List<Room> getByHotelId(long hotelId) throws SQLException, ServiceException{
        Connection connection = ConnectionUtil.getConnection();
        List<Room> rooms = null;
        try {
            connection.setAutoCommit(false);
            rooms = RoomDao.getInstance().getByHotelId(hotelId);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
                BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
                throw new ServiceException(e.getMessage());
            } catch (SQLException e1) {
                BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
                throw new ServiceException(e1.getMessage());
            }
        }
        return rooms;
    }

    public List<Room> getByBill(Bill bill) throws SQLException, ServiceException{
        Connection connection = ConnectionUtil.getConnection();
        List<Room> roomList = null;
        try {
            connection.setAutoCommit(false);
            roomList = RoomDao.getInstance().getByIdList(bill.getRoomIdList());
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            try {
                connection.rollback();
                BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
                throw new ServiceException(e.getMessage());
            } catch (SQLException e1) {
                BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
                throw new ServiceException(e1.getMessage());
            }
        }
        return roomList;
    }

    private void checkException(Connection connection, Exception e) throws ServiceException {
        try {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        } catch (SQLException e1) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.ROLLBACK_FAILED);
            throw new ServiceException(e1.getMessage());
        }
    }

}
