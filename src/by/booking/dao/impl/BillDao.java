package by.booking.dao.impl;

import by.booking.constants.Messages;
import by.booking.dao.interfaces.IBillDao;
import by.booking.utils.*;
import by.booking.entities.Bill;
import by.booking.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDao implements IBillDao{

    private static BillDao instance = null;

    private final String ADD_QUERY = "INSERT INTO BILLS (" +
            "ORDER_ID, ROOM_ID, PAYMENT_AMOUNT, BILL_STATUS)" +
            "VALUES(?, ?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT B.*, O.*, R.*, U.*, RT.*, H.*, L.*"+
            "FROM BILLS B " +
            "JOIN ORDERS O ON B.ORDER_ID = O.ORDER_ID " +
            "JOIN ROOMS R ON B.ROOM_ID = R.ROOM_ID " +
            "JOIN USERS U ON O.USER_ID = U.USER_ID " +
            "JOIN ROOMS_TYPES RT ON R.ROOM_TYPE_ID = RT.ROOM_TYPE_ID " +
            "JOIN HOTELS H ON R.HOTEL_ID = H.HOTEL_ID " +
            "JOIN LOCATIONS L ON H.LOCATION_ID = L.LOCATION_ID " +
            "WHERE B.BILL_ID = ?";
    private final String GET_BY_USER_ID_QUERY = "SELECT B.*, O.*, R.*, U.*, RT.*, H.*, L.*"+
            "FROM BILLS B " +
            "JOIN ORDERS O ON B.ORDER_ID = O.ORDER_ID " +
            "JOIN ROOMS R ON B.ROOM_ID = R.ROOM_ID " +
            "JOIN USERS U ON O.USER_ID = U.USER_ID " +
            "JOIN ROOMS_TYPES RT ON R.ROOM_TYPE_ID = RT.ROOM_TYPE_ID " +
            "JOIN HOTELS H ON R.HOTEL_ID = H.HOTEL_ID " +
            "JOIN LOCATIONS L ON H.LOCATION_ID = L.LOCATION_ID " +
            "WHERE U.USER_ID = ?";
    private final String UPDATE_QUERY = "UPDATE BILLS " +
            "SET ORDER_ID = ?, ROOM_ID = ?, PAYMENT_AMOUNT = ?, BILL_STATUS = ?" +
            "WHERE BILL_ID = ?;";
    private final String DELETE_QUERY = "UPDATE BILLS SET STATUS = 'deleted' WHERE ID = ?";

    private BillDao() {
    }

    public static BillDao getInstance() {
        if (instance == null) {
            instance = new BillDao();
        }
        return instance;
    }

    @Override
    public Bill add(Bill bill) throws DaoException {
        Connection connection = ConnectionUtil.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement stm = connection.prepareStatement(ADD_QUERY,
                Statement.RETURN_GENERATED_KEYS)) {
            stm.setLong(1, bill.getOrder().getId());
            stm.setLong(2, bill.getRoom().getId());
            stm.setInt(3, bill.getPaymentAmount());
            stm.setString(4, bill.getStatus());
            stm.executeUpdate();
            resultSet = stm.getGeneratedKeys();
            resultSet.next();
            bill.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.ADD_BILL_EXCEPTION);
            throw new DaoException(Messages.ADD_BILL_EXCEPTION, e);
        } finally {
            ClosingUtil.close(resultSet);
        }
        return bill;
    }

    @Override
    public Bill getById(long id) throws DaoException{
        Bill bill = null;
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_ID_QUERY)) {
            stm.setLong(1, id);
            ResultSet resultSet = stm.executeQuery();
            resultSet.next();
            bill = EntityBuilder.parseBill(resultSet);
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_BILL_EXCEPTION);
            throw new DaoException(Messages.GET_BILL_EXCEPTION, e);
        }
        return bill;
    }

    @Override
    public List<Bill> getAll() throws DaoException{
        
        return new ArrayList<>();
    }
    
    public List<Bill> getByUserId(long userId) throws DaoException{
        List<Bill> bills = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(GET_BY_USER_ID_QUERY)) {
            stm.setLong(1, userId);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                bills.add(EntityBuilder.parseBill(resultSet));
            }
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_BILL_EXCEPTION);
            throw new DaoException(Messages.GET_BILL_EXCEPTION, e);
        }
        return bills;
    }
    @Override
    public void update(Bill bill) throws DaoException{
        Connection connection = ConnectionUtil.getConnection();
        try (PreparedStatement stm = connection.prepareStatement(UPDATE_QUERY)) {
            stm.setLong(1, bill.getOrder().getId());
            stm.setLong(2, bill.getRoom().getId());
            stm.setInt(3, bill.getPaymentAmount());
            stm.setString(4, bill.getStatus());
            stm.setLong(5, bill.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            BookingSystemLogger.getInstance().logError(getClass(), Messages.GET_BILL_EXCEPTION);
            throw new DaoException(Messages.GET_BILL_EXCEPTION, e);
        }
    }

    @Override
    public void delete(Bill bill) throws DaoException{

    }
}
