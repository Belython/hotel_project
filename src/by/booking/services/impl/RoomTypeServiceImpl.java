package by.booking.services.impl;

import by.booking.constants.Messages;
import by.booking.dao.impl.RoomDao;
import by.booking.dao.impl.RoomTypeDao;
import by.booking.entities.Room;
import by.booking.entities.RoomType;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IRoomTypeService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomTypeServiceImpl implements IRoomTypeService {
    private static RoomTypeServiceImpl instance;

    private RoomTypeServiceImpl(){
    }

    public static synchronized RoomTypeServiceImpl getInstance(){
        if(instance == null){
            instance = new RoomTypeServiceImpl();
        }
        return instance;
    }
    @Override
    public void add(RoomType entity) throws SQLException, ServiceException {

    }

    @Override
    public List<RoomType> getAll() throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<RoomType> roomTypes = null;
        try {
            connection.setAutoCommit(false);
            roomTypes = RoomTypeDao.getInstance().getAll();
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return roomTypes;
    }

    @Override
    public RoomType getById(long id) throws SQLException, ServiceException {
        return null;
    }

    @Override
    public void update(RoomType roomType) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            RoomTypeDao.getInstance().update(roomType);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void delete(long id) throws SQLException, ServiceException {

    }
}