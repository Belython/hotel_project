package by.booking.utils;


import by.booking.constants.Statuses;
import by.booking.entities.RoomType;
import by.booking.entities.*;

import java.util.ArrayList;
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

    public static Hotel buildHotel(long hotelId, String hotelCountry, String hotelCity, String hotelName, String hotelStatus) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        hotel.setCountry(hotelCountry);
        hotel.setCity(hotelCity);
        hotel.setName(hotelName);
        hotel.setStatus(hotelStatus);
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

    public static Room buildRoom(long roomId, Hotel hotel, RoomType roomType, int roomNumber, long bookingStartDate,
                                 long bookingEndDate, String roomStatus) {
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

//    public static Bill buildBill(long billId, long userId, String userFirstName, String userLastName, String userEmail,
//                                 String userLogin, String userPassword, String userRole, String userStatus,
//                                 int totalPersons, long checkInDate, long checkOutDate, List<Long> roomIdList,
//                                 int paymentAmount, String billStatus) {
//        List<Room> roomList = new ArrayList<>();
//        for (Long roomId: roomIdList) {
//            Room room = new Room();
//            room.setId(roomId.longValue());
//            roomList.add(room);
//        }
//        User user = buildUser(userId, userFirstName, userLastName, userEmail, userLogin, userPassword, userRole, userStatus);
//        Bill bill = new Bill();
//        bill.setId(billId);
//        bill.setUser(user);
//        bill.setTotalPersons(totalPersons);
//        bill.setCheckInDate(checkInDate);
//        bill.setCheckOutDate(checkOutDate);
//        bill.setRoomList(roomList);
//        bill.setPaymentAmount(paymentAmount);
//        bill.setStatus(billStatus);
//        return bill;
//    }

    public static Bill buildBill(long billId, User user, int totalPersons, long checkInDate, long checkOutDate,
                                 List<Long> roomIdList, int paymentAmount, String billStatus) {
        List<Room> roomList = new ArrayList<>();
        for (Long roomId: roomIdList) {
            Room room = new Room();
            room.setId(roomId);
            roomList.add(room);
        }
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setUser(user);
        bill.setTotalPersons(totalPersons);
        bill.setCheckInDate(checkInDate);
        bill.setCheckOutDate(checkOutDate);
        bill.setRoomList(roomList);
        bill.setPaymentAmount(paymentAmount);
        bill.setStatus(billStatus);
        return bill;
    }

    public static Bill buildBill(Bill bill, List<Room> roomList) {
        bill.setRoomList(roomList);
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

}
