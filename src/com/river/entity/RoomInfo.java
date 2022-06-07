package com.river.entity;

public class RoomInfo {
    private int roomno;
    private String roomtype;
    private int bednum;
    private int price;
    private boolean booked;

    public RoomInfo() {
    }

    public RoomInfo(int roomno, String roomtype, int bednum, int price, boolean booked) {
        this.roomno = roomno;
        this.roomtype = roomtype;
        this.bednum = bednum;
        this.price = price;
        this.booked = booked;
    }

    public int getRoomno() {
        return roomno;
    }

    public void setRoomno(Integer roomno) {
        this.roomno = roomno;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public int getBednum() {
        return bednum;
    }

    public void setBednum(Integer bednum) {
        this.bednum = bednum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    @Override
    public String toString() {
        return "RoomInfo{" +
                "roomno=" + roomno +
                ", roomtype='" + roomtype + '\'' +
                ", bednum=" + bednum +
                ", price=" + price +
                ", booked=" + booked +
                '}';
    }
}
