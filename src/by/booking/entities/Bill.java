package by.booking.entities;

public class Bill {

    private long id;
    private int paymentAmount;
    private Order order;
    private Room room;
    private String status;

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (id != bill.id) return false;
        if (paymentAmount != bill.paymentAmount) return false;
        if (!order.equals(bill.order)) return false;
        if (!room.equals(bill.room)) return false;
        return status.equals(bill.status);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + paymentAmount;
        result = 31 * result + order.hashCode();
        result = 31 * result + room.hashCode();
        result = 31 * result + status.hashCode();
        return result;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

