package by.booking.services.impl;

import by.booking.constants.Messages;
import by.booking.dao.impl.OrderDao;
import by.booking.dao.impl.UserDao;
import by.booking.entities.Order;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IOrderService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private static OrderServiceImpl instance;

    private OrderServiceImpl(){
    }

    public static synchronized OrderServiceImpl getInstance(){
        if(instance == null){
            instance = new OrderServiceImpl();
        }
        return instance;
    }


    @Override
    public void add(Order order) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            OrderDao.getInstance().add(order);
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
    public List<Order> getAll() throws SQLException, ServiceException {
        return null;
    }

    @Override
    public Order getById(long id) throws SQLException, ServiceException {
        return null;
    }

    @Override
    public void update(Order order) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<Order> orders = null;
        try {
            connection.setAutoCommit(false);
            OrderDao.getInstance().update(order);
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

    public List<Order> getByUserId(long userId) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<Order> orders = null;
        try {
            connection.setAutoCommit(false);
            orders = OrderDao.getInstance().getByUserId(userId);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return orders;
    }

    public List<Order> getByStatus(String stauts) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<Order> orders = null;
        try {
            connection.setAutoCommit(false);
            orders = OrderDao.getInstance().getByStatus(stauts);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return orders;
    }

}
