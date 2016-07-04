package by.booking.utils;


import by.booking.constants.ColumnName;
import by.booking.constants.Statuses;
import by.booking.entities.RoomType;
import by.booking.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EntityBuilder {
    private EntityBuilder(){}

    public static User buildUser(long userId, String firstName, String lastName, String email, String login,
                                   String password, String role, String userStatus) {
        User user = new User();
        user.setId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setRole(role);
        user.setStatus(userStatus);
        return user;
    }

    public static User parseUser(ResultSet rs) throws SQLException {
        long userId = rs.getLong(ColumnName.USER_ID);
        String userFirstName = rs.getString(ColumnName.USER_FIRST_NAME);
        String userLastName = rs.getString(ColumnName.USER_LAST_NAME);
        String userEmail = rs.getString(ColumnName.USER_EMAIL);
        String userLogin = rs.getString(ColumnName.USER_LOGIN);
        String userPassword = rs.getString(ColumnName.USER_PASSWORD);
        String userRole = rs.getString(ColumnName.USER_ROLE);
        String userStatus = rs.getString(ColumnName.USER_STATUS);
        User user = buildUser(userId, userFirstName, userLastName, userEmail, userLogin, userPassword, userRole, userStatus);
        return user;
    }

    public static Hotel buildHotel(long hotelId, String hotelCountry, String hotelCity, String hotelName, String hotelStatus) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        hotel.setCountry(hotelCountry);
        hotel.setCity(hotelCity);
        hotel.setName(hotelName);
        hotel.setStatus(hotelStatus);
        return hotel;
    }

    public static Hotel parseHotel(ResultSet rs) throws SQLException{
        long hotelId = rs.getLong(ColumnName.HOTEL_ID);
        String hotelCountry = rs.getString(ColumnName.LOCATION_COUNTRY);
        String hotelCity = rs.getString(ColumnName.LOCATION_CITY);
        String hotelName = rs.getString(ColumnName.HOTEL_NAME);
        String hotelStatus = rs.getString(ColumnName.HOTEL_STATUS);
        Hotel hotel = buildHotel(hotelId, hotelCountry, hotelCity, hotelName, hotelStatus);
        return hotel;
    }

    public static RoomType buildRoomType(long roomTypeId, String roomTypeName, int maxPersons, int roomPricePerNight,
                                         List<String> facilities, String roomTypeStatus) {
        RoomType roomType = new RoomType();
        roomType.setId(roomTypeId);
        roomType.setRoomTypeName(roomTypeName);
        roomType.setMaxPersons(maxPersons);
        roomType.setRoomPricePerNight(roomPricePerNight);
        roomType.setFacilities(facilities);
        roomType.setStatus(roomTypeStatus);
        return roomType;
    }

    public static RoomType parseRoomType(ResultSet rs) throws SQLException {
        long roomTypeId = rs.getLong(ColumnName.ROOM_TYPE_ID);
        String roomTypeName = rs.getString(ColumnName.ROOM_TYPE_NAME);
        int maxPersons = rs.getInt(ColumnName.ROOM_TYPE_MAX_PERSONS);
        int roomPricePerNight = rs.getInt(ColumnName.ROOM_TYPE_PRICE_PER_NIGHT);
        List<String> facilities = SerializationUtil.deserialize(rs.getBlob(ColumnName.ROOM_TYPE_FACILITIES));
        String roomTypeStatus = rs.getString(ColumnName.ROOM_TYPE_STATUS);
        RoomType roomType = buildRoomType(roomTypeId, roomTypeName, maxPersons, roomPricePerNight, facilities, roomTypeStatus);
        return roomType;
    }

    public static Room buildRoom(long roomId, long hotelId, long roomTypeId, int roomNumber, long bookingStartDate,
                                 long bookingEndDate, String roomStatus, String hotelCountry, String hotelCity,
                                 String hotelName, String hotelStatus, String roomTypeName, int maxPersons,
                                 int roomPricePerNight, List<String> facilities, String roomTypeStatus) {
        Hotel hotel = buildHotel(hotelId, hotelCountry, hotelCity, hotelName, hotelStatus);
        RoomType roomType = buildRoomType(roomTypeId, roomTypeName, maxPersons, roomPricePerNight, facilities, roomTypeStatus);
        Room room = new Room();
        room.setId(roomId);
        room.setHotel(hotel);
        room.setRoomType(roomType);
        room.setRoomNumber(roomNumber);
        room.setBookingStartDate(bookingStartDate);
        room.setBookingEndDate(bookingEndDate);
        room.setStatus(roomStatus);
        return room;
    }

    public static Room parseRoom(ResultSet rs) throws SQLException {
        long roomId = rs.getLong(ColumnName.ROOM_ID);
        long hotelId = rs.getLong(ColumnName.ROOM_HOTEL_ID);
        long roomTypeId = rs.getLong(ColumnName.ROOM_ROOM_TYPE_ID);
        int roomNumber = rs.getInt(ColumnName.ROOM_NUMBER);
        long bookingStartDate = rs.getLong(ColumnName.ROOM_BOOKING_START_DATE);
        long bookingEndDate = rs.getLong(ColumnName.ROOM_BOOKING_END_DATE);
        String roomStatus = rs.getString(ColumnName.ROOM_STATUS);

        String hotelCountry = rs.getString(ColumnName.LOCATION_COUNTRY);
        String hotelCity = rs.getString(ColumnName.LOCATION_CITY);
        String hotelName = rs.getString(ColumnName.HOTEL_NAME);
        String hotelStatus = rs.getString(ColumnName.HOTEL_STATUS);

        String roomTypeName = rs.getString(ColumnName.ROOM_TYPE_NAME);
        int maxPersons = rs.getInt(ColumnName.ROOM_TYPE_MAX_PERSONS);
        int roomPricePerNight = rs.getInt(ColumnName.ROOM_TYPE_PRICE_PER_NIGHT);
        List<String> facilities = SerializationUtil.deserialize(rs.getBlob(ColumnName.ROOM_TYPE_FACILITIES));
        String roomTypeStatus = rs.getString(ColumnName.ROOM_TYPE_STATUS);

        Room room = buildRoom(roomId, hotelId, roomTypeId, roomNumber, bookingStartDate, bookingEndDate, roomStatus,
                hotelCountry, hotelCity, hotelName, hotelStatus, roomTypeName, maxPersons, roomPricePerNight,
                facilities, roomTypeStatus);
        return room;

    }

    public static Order buildOrder(long orderId, long userId, long hotelId, long roomTypeId, int totalPersons,
                                   long checkInDate, long checkOutDate, String orderStatus, String userFirstName,
                                   String userLastName, String userEmail, String userLogin, String userPassword,
                                   String userRole, String userStatus, String roomTypeName, int maxPersons, int roomPricePerNight,
                                   List<String> facilities, String roomTypeStatus, String hotelCountry, String hotelCity,
                                   String hotelName, String hotelStatus) {
        User user = buildUser(userId, userFirstName, userLastName, userEmail, userLogin, userPassword, userRole, userStatus);
        Hotel hotel = buildHotel(hotelId, hotelCountry, hotelCity, hotelName, hotelStatus);
        RoomType roomType = buildRoomType(roomTypeId, roomTypeName, maxPersons, roomPricePerNight, facilities, roomTypeStatus);
        Order order = new Order();
        order.setId(orderId);
        order.setUser(user);
        order.setHotel(hotel);
        order.setRoomType(roomType);
        order.setTotalPersons(totalPersons);
        order.setCheckInDate(checkInDate);
        order.setCheckOutDate(checkOutDate);
        order.setStatus(orderStatus);
        return order;
    }

    public static Order buildOrder(User user, int totalPersons, RoomType roomType, Hotel hotel,
                                   long checkInDate, long checkOutDate) {
        Order order = new Order();
        order.setUser(user);
        order.setTotalPersons(totalPersons);
        order.setRoomType(roomType);
        order.setHotel(hotel);
        order.setCheckInDate(checkInDate);
        order.setCheckOutDate(checkOutDate);
        order.setStatus(Statuses.ORDER_NEW);
        return order;
    }

    public static Order parseOrder(ResultSet rs) throws SQLException {
        long orderId = rs.getLong(ColumnName.ORDER_ID);
        long userId = rs.getLong(ColumnName.ORDER_USER_ID);
        long hotelId = rs.getLong(ColumnName.ORDER_HOTEL_ID);
        long roomTypeId = rs.getLong(ColumnName.ORDER_ROOM_TYPE_ID);
        int totalPersons = rs.getInt(ColumnName.ORDER_TOTAL_PERSONS);
        long checkInDate = rs.getLong(ColumnName.ORDER_CHECK_IN_DATE);
        long checkOutDate = rs.getLong(ColumnName.ORDER_CHECK_OUT_DATE);
        String orderStatus = rs.getString(ColumnName.ORDER_STATUS);

        String userFirstName = rs.getString(ColumnName.USER_FIRST_NAME);
        String userLastName = rs.getString(ColumnName.USER_LAST_NAME);
        String userEmail = rs.getString(ColumnName.USER_EMAIL);
        String userLogin = rs.getString(ColumnName.USER_LOGIN);
        String userPassword = rs.getString(ColumnName.USER_PASSWORD);
        String userRole = rs.getString(ColumnName.USER_ROLE);
        String userStatus = rs.getString(ColumnName.USER_STATUS);

        String roomTypeName = rs.getString(ColumnName.ROOM_TYPE_NAME);
        int maxPersons = rs.getInt(ColumnName.ROOM_TYPE_MAX_PERSONS);
        int roomPricePerNight = rs.getInt(ColumnName.ROOM_TYPE_PRICE_PER_NIGHT);
        List<String> facilities = SerializationUtil.deserialize(rs.getBlob(ColumnName.ROOM_TYPE_FACILITIES));
        String roomTypeStatus = rs.getString(ColumnName.ROOM_TYPE_STATUS);

        String hotelCountry = rs.getString(ColumnName.LOCATION_COUNTRY);
        String hotelCity = rs.getString(ColumnName.LOCATION_CITY);
        String hotelName = rs.getString(ColumnName.HOTEL_NAME);
        String hotelStatus = rs.getString(ColumnName.HOTEL_STATUS);

        Order order = buildOrder(orderId, userId, hotelId, roomTypeId, totalPersons, checkInDate, checkOutDate, orderStatus,
                userFirstName, userLastName, userEmail, userLogin, userPassword, userRole, userStatus, roomTypeName, maxPersons,
                roomPricePerNight, facilities, roomTypeStatus, hotelCountry, hotelCity, hotelName, hotelStatus);
        return order;
    }

    public static Bill buildBill(long billId, long orderId, long roomId, int paymentAmount, String billStatus,
                                 long userId, int totalPersons, long checkInDate, long checkOutDate, String orderStatus,
                                 String userFirstName, String userLastName, String userEmail, String userLogin,
                                 String userPassword, String userRole, String userStatus, long hotelId, long roomTypeId, int roomNumber,
                                 long bookingStartDate, long bookingEndDate, String roomStatus, String roomTypeName, int maxPersons, int roomPricePerNight,
                                 List<String> facilities, String roomTypeStatus, String hotelCountry, String hotelCity,
                                 String hotelName, String hotelStatus) {
        Order order = buildOrder(orderId, userId, hotelId, roomTypeId, totalPersons, checkInDate, checkOutDate, orderStatus,
                userFirstName, userLastName, userEmail, userLogin, userPassword, userRole, userStatus, roomTypeName, maxPersons,
                roomPricePerNight, facilities, roomTypeStatus, hotelCountry, hotelCity, hotelName, hotelStatus);
        Room room = buildRoom(roomId, hotelId, roomTypeId, roomNumber, bookingStartDate, bookingEndDate,
                roomStatus, hotelCountry, hotelCity, hotelName, hotelStatus, roomTypeName, maxPersons, roomPricePerNight,
                facilities, roomTypeStatus);
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setOrder(order);
        bill.setRoom(room);
        bill.setPaymentAmount(paymentAmount);
        bill.setStatus(billStatus);
        return bill;
    }

    public static Bill buildNewBill(int paymentAmount, Order order, Room room) {
        Bill bill = new Bill();
        bill.setPaymentAmount(paymentAmount);
        bill.setOrder(order);
        bill.setRoom(room);
        bill.setStatus(Statuses.BILL_NOT_PAID);
        return bill;
    }

    public static Bill parseBill(ResultSet rs) throws SQLException {
        long billId = rs.getLong(ColumnName.BILL_ID);
        long orderId = rs.getLong(ColumnName.BILL_ORDER_ID);
        long roomId = rs.getLong(ColumnName.BILL_ROOM_ID);
        int paymenAmount = rs.getInt(ColumnName.BILL_PAYMENT_AMOUNT);
        String billStatus = rs.getString(ColumnName.BILL_STATUS);

        long userId = rs.getLong(ColumnName.ORDER_USER_ID);
        int totalPersons = rs.getInt(ColumnName.ORDER_TOTAL_PERSONS);
        long checkInDate = rs.getLong(ColumnName.ORDER_CHECK_IN_DATE);
        long checkOutDate = rs.getLong(ColumnName.ORDER_CHECK_OUT_DATE);
        String orderStatus = rs.getString(ColumnName.ORDER_STATUS);

        String userFirstName = rs.getString(ColumnName.USER_FIRST_NAME);
        String userLastName = rs.getString(ColumnName.USER_LAST_NAME);
        String userEmail = rs.getString(ColumnName.USER_EMAIL);
        String userLogin = rs.getString(ColumnName.USER_LOGIN);
        String userPassword = rs.getString(ColumnName.USER_PASSWORD);
        String userRole = rs.getString(ColumnName.USER_ROLE);
        String userStatus = rs.getString(ColumnName.USER_STATUS);

        long hotelId = rs.getLong(ColumnName.ROOM_HOTEL_ID);
        long roomTypeId = rs.getLong(ColumnName.ROOM_ROOM_TYPE_ID);
        int roomNumber = rs.getInt(ColumnName.ROOM_NUMBER);
        long bookingStartDate = rs.getLong(ColumnName.ROOM_BOOKING_START_DATE);
        long bookingEndDate = rs.getLong(ColumnName.ROOM_BOOKING_END_DATE);
        String roomStatus = rs.getString(ColumnName.ROOM_STATUS);

        String roomTypeName = rs.getString(ColumnName.ROOM_TYPE_NAME);
        int maxPersons = rs.getInt(ColumnName.ROOM_TYPE_MAX_PERSONS);
        int roomPricePerNight = rs.getInt(ColumnName.ROOM_TYPE_PRICE_PER_NIGHT);
        List<String> facilities = SerializationUtil.deserialize(rs.getBlob(ColumnName.ROOM_TYPE_FACILITIES));
        String roomTypeStatus = rs.getString(ColumnName.ROOM_TYPE_STATUS);

        String hotelCountry = rs.getString(ColumnName.LOCATION_COUNTRY);
        String hotelCity = rs.getString(ColumnName.LOCATION_CITY);
        String hotelName = rs.getString(ColumnName.HOTEL_NAME);
        String hotelStatus = rs.getString(ColumnName.HOTEL_STATUS);

        Bill bill = buildBill(billId, orderId, roomId, paymenAmount, billStatus, userId, totalPersons, checkInDate,
                checkOutDate, orderStatus, userFirstName, userLastName, userEmail, userLogin, userPassword, userRole,
                userStatus, hotelId, roomTypeId, roomNumber, bookingStartDate, bookingEndDate, roomStatus, roomTypeName,
                maxPersons, roomPricePerNight, facilities, roomTypeStatus, hotelCountry, hotelCity, hotelName, hotelStatus);
        return bill;
    }


}
