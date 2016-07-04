package by.booking.dao.impl;

import by.booking.constants.Messages;
import by.booking.dao.interfaces.IOrderDao;
import by.booking.entities.Bill;
import by.booking.utils.*;
import by.booking.entities.Order;
import by.booking.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements IOrderDao {

    private static OrderDao instance = null;

    private final String ADD_QUERY = "INSERT INTO ORDERS (" +
            "USER_ID, HOTEL_ID, ROOM_TYPE_ID, TOTAL_PERSONS, CHECK_IN_DATE, CHECK_OUT_DATE, ORDER_STATUS)" +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT O.*, U.*, RT.*, H.*, L.*"+
            "FROM ORDERS O " +
            "JOIN USERS U ON O.USER_ID = U.USER_ID " +
            "JOIN ROOMS_TYPES RT ON O.ROOM_TYPE_ID = RT.ROOM_TYPE_ID " +
            "JOIN HOTELS H ON O.HOTEL_ID = H.HOTEL_ID " +
            "JOIN LOCATIONS L ON H.LOCATION_ID = L.LOCATION_ID " +
            "WHERE O.ORDER_ID = ?";
    private final String GET_BY_USER_ID_QUERY = "SELECT O.*, U.*, RT.*, H.*, L.*"+
            "FROM ORDERS O " +
            "JOIN USERS U ON O.USER_ID = U.USER_ID " +
            "JOIN ROOMS_TYPES RT ON O.ROOM_TYPE_ID = RT.ROOM_TYPE_ID " +
            "JOIN HOTELS H ON O.HOTEL_ID = H.HOTEL_ID " +
            "JOIN LOCATIONS L ON H.LOCATION_ID = L.LOCATION_ID " +
            "WHERE U.USER_ID = ?";
    private final String GET_BY_STATUS_QUERY = "SELECT O.*, U.*, RT.*, H.*, L.*"+
            "FROM ORDERS O " +
            "JOIN USERS U ON O.USER_ID = U.USER_ID " +
            "JOIN ROOMS_TYPES RT ON O.ROOM_TYPE_ID = RT.ROOM_TYPE_ID " +
            "JOIN HOTELS H ON O.HOTEL_ID = H.HOTEL_ID " +
            "JOIN LOCATIONS L ON H.LOCATION_ID = L.LOCATION_ID " +
            "WHERE O.ORDER_STATUS = ?";
    private final String UPDATE_QUERY = "UPDATE ORDERS " +
            "SET USER_ID = ?, HOTEL_ID = ?, ROOM_TYPE_ID = ?, TOTAL_PERSONS = ?, CHECK_IN_DATE = ?, CHECK_OUT_DATE = ?," +
            "ORDER_STATUS = ?" +
            "WHERE ORDER_ID = ?";
    private final String DELETE_QUERY = "UPDATE ORDERS SET STATUS = 'deleted' WHERE ID = ?";
    
    private OrderDao() {
    }

    public static OrderDao getInstance() {
        if (instance == null) {
            instance = new OrderDao();
        }
        return instance;
    }

    @Override
    public Order add(Order order) throws DaoException{
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement stm = connection.prepareStatement(ADD_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            stm.setLong(1, order.getUser().getId());
            stm.setLong(2, order.getHotel().getId());
            stm.setLong(3, order.getRoomType().getId());
            stm.setInt(4, order.getTotalPersons());
            stm.setLong(5, order.getCheckInDate());
            stm.setLong(6, order.getCheckOutDate());
            stm.setString(7, order.getStatus());
            stm.executeUpdate();
            resultSet = stm.getGeneratedKeys();
            resultSet.next();
            order.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.ADD_ORDER_EXCEPTION);
            throw new DaoException(Messages.ADD_ORDER_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return order;
    }

    @Override
    public Order getById(long id) throws DaoException{
        Order order = null;
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_ID_QUERY)) {
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            order = EntityBuilder.parseOrder(resultSet);
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_ORDER_EXCEPTION);
            throw new DaoException(Messages.GET_ORDER_EXCEPTION, e);
        }
        return order;
    }

    @Override
    public List<Order> getAll() throws DaoException{
        
        return new ArrayList<>();
    }

    public List<Order> getByUserId(long userId) throws DaoException{
        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_USER_ID_QUERY)) {
            stm.setLong(1, userId);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                orders.add(EntityBuilder.parseOrder(resultSet));
            }
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_ORDER_EXCEPTION);
            throw new DaoException(Messages.GET_ORDER_EXCEPTION, e);
        }
        return orders;
    }
    @Override
    public void update(Order order) throws DaoException{
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(UPDATE_QUERY)) {
            stm.setLong(1, order.getUser().getId());
            stm.setLong(2, order.getHotel().getId());
            stm.setLong(3, order.getRoomType().getId());
            stm.setInt(4, order.getTotalPersons());
            stm.setLong(5, order.getCheckInDate());
            stm.setLong(6, order.getCheckOutDate());
            stm.setString(7, order.getStatus());
            stm.setLong(8, order.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.UPDATE_ORDER_EXCEPTION);
            throw new DaoException(Messages.UPDATE_ORDER_EXCEPTION, e);
        }
    }

    @Override
    public void delete(Order order) throws DaoException{

    }

    public List<Order> getByStatus(String status) throws DaoException{
        List<Order> orders = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_STATUS_QUERY)) {
            stm.setString(1, status);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                orders.add(EntityBuilder.parseOrder(resultSet));
            }
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_ORDER_EXCEPTION);
            throw new DaoException(Messages.GET_ORDER_EXCEPTION, e);
        }
        return orders;
    }


}
