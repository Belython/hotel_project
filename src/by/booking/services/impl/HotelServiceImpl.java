package by.booking.services.impl;

import by.booking.constants.Messages;
import by.booking.dao.impl.HotelDao;
import by.booking.entities.Hotel;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IHotelService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;
import by.booking.utils.ExceptionHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class HotelServiceImpl implements IHotelService{

    private static HotelServiceImpl instance;

    private HotelServiceImpl(){
    }

    public static synchronized HotelServiceImpl getInstance(){
        if(instance == null){
            instance = new HotelServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(Hotel entity) throws ServiceException {
        
    }

    @Override
    public List<Hotel> getAll() throws ServiceException {
        return null;
    }

    @Override
    public Hotel getById(long id) throws ServiceException {
        return null;
    }

    @Override
    public void update(Hotel entity) throws ServiceException {

    }

    @Override
    public void delete(long id) throws ServiceException {

    }
    
    public Hotel getByHotelName(String hotelName) throws ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        Hotel hotel = null;
        try {
            connection.setAutoCommit(false);
            hotel = HotelDao.getInstance().getByHotelName(hotelName);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        } catch (SQLException | DaoException e) {
            ExceptionHandler.getServiceHandler(connection, e, getClass());
        }
        return hotel;
    }
}
