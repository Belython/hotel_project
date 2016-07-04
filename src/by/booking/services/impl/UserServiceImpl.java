package by.booking.services.impl;

import by.booking.constants.Messages;
import by.booking.dao.impl.UserDao;
import by.booking.entities.User;
import by.booking.exceptions.DaoException;
import by.booking.exceptions.ServiceException;
import by.booking.services.interfaces.IUserService;
import by.booking.utils.BookingSystemLogger;
import by.booking.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements IUserService {
    private static UserServiceImpl instance;

    private UserServiceImpl(){
    }

    public static synchronized UserServiceImpl getInstance(){
        if(instance == null){
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void add(User user) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            UserDao.getInstance().add(user);
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
    public List<User> getAll() throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        List<User> users = null;
        try {
            connection.setAutoCommit(false);
            users = UserDao.getInstance().getAll();
            for (User user: users) {
                users.add(user);
            }
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(long id) throws SQLException {
        throw new UnsupportedOperationException();
    }


    @Override
    public void update(User entity) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    public boolean checkUserAuthorization(String login, String password) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        boolean isAuthorized = false;
        try {
            connection.setAutoCommit(false);
            isAuthorized = UserDao.getInstance().isAuthorized(login, password);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return isAuthorized;
    }

    public User getUserByLogin(String login) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        User user = null;
        try {
            connection.setAutoCommit(false);
            user = UserDao.getInstance().getByLogin(login);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    public boolean checkIsNewUser(User user) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        boolean isNew = false;
        try {
            connection.setAutoCommit(false);
            if (UserDao.getInstance().isNewUser(user.getLogin())) {
                isNew = true;
            }
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
        return isNew;
    }

    public void registrateUser(User user) throws SQLException, ServiceException {
        Connection connection = ConnectionUtil.getConnection();
        try {
            connection.setAutoCommit(false);
            user.setRole("client");
            user.setStatus("active");
            UserDao.getInstance().add(user);
            connection.commit();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_SUCCEEDED);
        }
        catch (SQLException | DaoException e) {
            connection.rollback();
            BookingSystemLogger.getInstance().logError(getClass(), Messages.TRANSACTION_FAILED);
            throw new ServiceException(e.getMessage());
        }
    }
}
