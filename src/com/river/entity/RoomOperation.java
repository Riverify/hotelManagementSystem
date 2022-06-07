package com.river.entity;

import java.sql.Date;

public class RoomOperation {
    private int business;
    private String idnum;
    private int roomnum;
    private Date enterdate;
    private Date outdate;
    private String status;

    public RoomOperation() {
    }

    public RoomOperation(int business, String idnum, int roomnum, Date enterdate, Date outdate, String status) {
        this.business = business;
        this.idnum = idnum;
        this.roomnum = roomnum;
        this.enterdate = enterdate;
        this.outdate = outdate;
        this.status = status;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public int getRoomnum() {
        return roomnum;
    }

    public void setRoomnum(Integer roomnum) {
        this.roomnum = roomnum;
    }

    public Date getEnterdate() {
        return enterdate;
    }

    public void setEnterdate(Date enterdate) {
        this.enterdate = enterdate;
    }

    public Date getOutdate() {
        return outdate;
    }

    public void setOutdate(Date outdate) {
        this.outdate = outdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "roomOperation{" +
                "business=" + business +
                ", idnum='" + idnum + '\'' +
                ", roomnum=" + roomnum +
                ", enterdate=" + enterdate +
                ", outdate=" + outdate +
                ", status='" + status + '\'' +
                '}';
    }

}
