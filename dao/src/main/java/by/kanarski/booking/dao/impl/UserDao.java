package by.kanarski.booking.dao.impl;

import by.kanarski.booking.constants.ColumnName;
import by.kanarski.booking.constants.Messages;
import by.kanarski.booking.dao.interfaces.IUserDao;
import by.kanarski.booking.entities.User;
import by.kanarski.booking.exceptions.DaoException;
import by.kanarski.booking.utils.BookingSystemLogger;
import by.kanarski.booking.utils.ClosingUtil;
import by.kanarski.booking.utils.ConnectionUtil;
import by.kanarski.booking.utils.EntityParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao {

    private static UserDao instance = null;
    private final String ADD_QUERY = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, EMAIL, LOGIN, PASSWORD, ROLE, USER_STATUS)" +
            " VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM USERS WHERE USER_ID = ?";
    private final String GET_BY_LOGIN_QUERY = "SELECT * FROM USERS WHERE LOGIN = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM USERS WHERE USER_STATUS = 'active'";
    private final String UPDATE_QUERY = "UPDATE USERS SET FIRST_NAME = ?, LAST_NAME = ?, " +
            "EMAIL = ?, LOGIN = ?, PASSWORD = ?, USER_STATUS = ? WHERE USER_ID = ?";
    private final String DELETE_QUERY = "UPDATE USERS SET USER_STATUS = 'deleted' WHERE USER_ID = ?";
    private final String CHECK_AUTHORIZATION_QUERY = "SELECT LOGIN, PASSWORD FROM USERS WHERE LOGIN = ? AND PASSWORD = ?";
    private final String CHECK_LOGIN_QUERY = "SELECT LOGIN FROM USERS WHERE LOGIN = ?";

    private UserDao() {
    }

    public static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public User add(User user) throws DaoException {
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement stm = connection.prepareStatement(ADD_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getLogin());
            stm.setString(5, user.getPassword());
            stm.setString(6, user.getRole());
            stm.setString(7, user.getStatus());
            stm.executeUpdate();
            resultSet = stm.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.ADD_USER_EXCEPTION);
            throw new DaoException(Messages.ADD_USER_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return user;
    }

    @Override
    public User getById(long id) throws DaoException {
        User user = null;
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_ID_QUERY)) {
            stm.setLong(1, id);
            resultSet = stm.executeQuery();
            resultSet.next();
            user = EntityParser.parseUser(resultSet);
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_USER_EXCEPTION);
            throw new DaoException(Messages.GET_USER_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return user;
    }

    public User getByLogin(String login) throws DaoException {
        User user = null;
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_LOGIN_QUERY)) {
            stm.setString(1, login);
            resultSet = stm.executeQuery();
            resultSet.next();
            user = EntityParser.parseUser(resultSet);
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_USER_EXCEPTION);
            throw new DaoException(Messages.GET_USER_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return user;
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> list = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement stm = connection.prepareStatement(GET_ALL_QUERY)) {
            resultSet = stm.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString(ColumnName.USER_FIRST_NAME));
                user.setLastName(resultSet.getString(ColumnName.USER_LAST_NAME));
                list.add(user);
            }
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_USERS_EXCEPTION);
            throw new DaoException(Messages.GET_USERS_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return list;
    }

    @Override
    public void update(User user) throws DaoException {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(UPDATE_QUERY)) {
            stm.setString(1, user.getFirstName());
            stm.setString(2, user.getLastName());
            stm.setString(3, user.getEmail());
            stm.setString(4, user.getLogin());
            stm.setString(5, user.getPassword());
            stm.setLong(6, user.getId());
            stm.setString(7, user.getStatus());
            stm.executeUpdate();
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.UPDATE_USER_EXCEPTION);
            throw new DaoException(Messages.UPDATE_USER_EXCEPTION, e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(DELETE_QUERY)) {
            stm.setLong(1, user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.DELETE_USER_EXCEPTION);
            throw new DaoException(Messages.DELETE_USER_EXCEPTION, e);
        }
    }

    public boolean isAuthorized(String login, String password) throws DaoException {
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        boolean isLogIn = false;
        try (PreparedStatement stm = connection.prepareStatement(CHECK_AUTHORIZATION_QUERY)) {
            stm.setString(1, login);
            stm.setString(2, password);
            resultSet = stm.executeQuery();
            if (resultSet.next()) {
                isLogIn = true;
            }
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.CHECK_USER_AUTHORIZATION_EXCEPTION);
            throw new DaoException(Messages.CHECK_USER_AUTHORIZATION_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return isLogIn;
    }

    public boolean isNewUser(String login) throws DaoException {
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        boolean isNew = true;
        try (PreparedStatement stm = connection.prepareStatement(CHECK_LOGIN_QUERY)) {
            stm.setString(1, login);
            resultSet = stm.executeQuery();
            if (resultSet.next()) {
                isNew = false;
            }
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.CHECK_IS_NEW_USER_EXCEPTION);
            throw new DaoException(Messages.CHECK_IS_NEW_USER_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return isNew;
    }
}
