package by.booking.dto;

import by.booking.constants.Statuses;
import by.booking.entities.Hotel;
import by.booking.entities.Room;
import by.booking.entities.RoomType;
import by.booking.utils.EntityBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelDto {

    private long hotelId;
    private String hotelCountry;
    private String hotelCity;
    private String hotelName;
    private List<Room> rooms = new ArrayList<>();
    private List<RoomType> roomTypes = new ArrayList<>();
    private HashMap<RoomType, Integer> roomTypesCount = new HashMap<>();
    private int roomsCount;

    public HotelDto(long hotelId, String hotelCountry, String hotelCity, String hotelName, List<Room> rooms) {
        this.hotelId = hotelId;
        this.hotelCountry = hotelCountry;
        this.hotelCity = hotelCity;
        this.hotelName = hotelName;
        this.rooms = rooms;
        this.roomsCount = rooms.size();
        setRoomTypes();
    }


    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelCountry() {
        return hotelCountry;
    }

    public void setHotelCountry(String hotelCountry) {
        this.hotelCountry = hotelCountry;
    }

    public String getHotelCity() {
        return hotelCity;
    }

    public void setHotelCity(String hotelCity) {
        this.hotelCity = hotelCity;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        this.roomsCount = rooms.size();
    }

    public List<RoomType> getRoomTypes() {
        return roomTypes;
    }

    private void setRoomTypes() {
        int counter = 0;
        for (int i = 0; i < rooms.size(); i++) {
            counter++;
            RoomType currentRoomType = rooms.get(i).getRoomType();
            if (i < (rooms.size() - 1)) {
                String currentRoomTypeName = currentRoomType.getRoomTypeName();
                String nextRoomTypeName = rooms.get(i + 1).getRoomType().getRoomTypeName();
                if (!nextRoomTypeName.equals(currentRoomTypeName)) {
                    roomTypes.add(rooms.get(i).getRoomType());
                    roomTypesCount.put(currentRoomType, counter);
                    counter = 0;
                }
            } else {
                roomTypes.add(rooms.get(i).getRoomType());
                roomTypesCount.put(currentRoomType, counter);
            }
        }
    }

    public HashMap<RoomType, Integer> getRoomTypesCount() {
        return roomTypesCount;
    }

    public void setRoomTypesCount(HashMap<RoomType, Integer> roomTypesCount) {
        this.roomTypesCount = roomTypesCount;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public RoomType getRoomTypeById(long roomTypeId) {
        for (RoomType roomType: roomTypes) {
            if(roomType.getId() == roomTypeId) {
                return roomType;
            }
        }
        return null;
    }

    public Hotel getHotel() {
        Hotel hotel = EntityBuilder.buildHotel(hotelId, hotelCountry, hotelCity, hotelName, Statuses.HOTEL_AVAILABLE);
        return hotel;
    }

}
