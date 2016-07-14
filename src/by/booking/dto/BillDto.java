package by.booking.dto;

import by.booking.entities.Room;
import by.booking.entities.User;

import java.util.List;

public class BillDto {

    private long id;
    private User user;
    private List<Long> reservedRoomsIds;
    private int paymentAmount;
    private long checkInDate;
    private long checkOutDate;

    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(long checkInDate) {
        this.checkInDate = checkInDate;
    }

    public long getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(long checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public List<Long> getReservedRoomsIds() {
        return reservedRoomsIds;
    }

    public void setReservedRoomsIds(List<Long> reservedRoomsIds) {
        this.reservedRoomsIds = reservedRoomsIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

