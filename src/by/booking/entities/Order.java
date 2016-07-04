package by.booking.entities;

public class Order{

    private long id;
    private User user;
    private int totalPersons;
    private RoomType roomType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (totalPersons != order.totalPersons) return false;
        if (checkInDate != order.checkInDate) return false;
        if (checkOutDate != order.checkOutDate) return false;
        if (!user.equals(order.user)) return false;
        if (!roomType.equals(order.roomType)) return false;
        if (!hotel.equals(order.hotel)) return false;
        return status.equals(order.status);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + user.hashCode();
        result = 31 * result + totalPersons;
        result = 31 * result + roomType.hashCode();
        result = 31 * result + hotel.hashCode();
        result = 31 * result + (int) (checkInDate ^ (checkInDate >>> 32));
        result = 31 * result + (int) (checkOutDate ^ (checkOutDate >>> 32));
        result = 31 * result + status.hashCode();
        return result;
    }

    private Hotel hotel;
    private long checkInDate;
    private long checkOutDate;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getTotalPersons() {
        return this.totalPersons;
    }

    public void setTotalPersons(int totalPersons) {
        this.totalPersons = totalPersons;
    }

    public long getCheckInDate() {
        return this.checkInDate;
    }

    public void setCheckInDate(long checkInDate) {
        this.checkInDate = checkInDate;
    }

    public long getCheckOutDate() {
        return this.checkOutDate;
    }

    public void setCheckOutDate(long checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
