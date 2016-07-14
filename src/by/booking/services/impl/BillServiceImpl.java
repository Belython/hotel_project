package by.booking.services.impl;

import by.booking.constants.Messages;
import by.booking.dao.impl.BillDao;
import by.booking.dto.BillDto;
import by.booking.entities.Bill;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IBillService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class BillServiceImpl implements IBillService {

    private static BillServiceImpl instance;

    private BillServiceImpl(){
    }

    public static synchronized BillServiceImpl getInstance(){
        if(instance == null){
            instance = new BillServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(Bill bill) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            BillDao.getInstance().add(bill);
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
    public List<Bill> getAll() throws SQLException, ServiceException {
        return null;
    }

    @Override
    public Bill getById(long id) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        Bill bill = null;
        try {
            connection.setAutoCommit(false);
            billDto = BillDao.getInstance().getById(id);
            List<Long> roomsIds = billDto.getRoomsIds();

            bill = BillDao.getInstance().getById(id);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return bill;
    }

    @Override
    public void update(Bill bill) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            BillDao.getInstance().update(bill);
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

    public List<Bill> getByUserId(long userId) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<Bill> bills = null;
        try {
            connection.setAutoCommit(false);
            bills = BillDao.getInstance().getByUserId(userId);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return bills;
    }


    @Override
    public Bill getById(long id) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        Bill bill = null;
        try {
            connection.setAutoCommit(false);
            BillDto billDto = BillDao.getInstance().getById(id);
            List<Long> roomsIds = billDto.getRoomsIds();

            bill = BillDao.getInstance().getById(id);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return bill;
    }
}
