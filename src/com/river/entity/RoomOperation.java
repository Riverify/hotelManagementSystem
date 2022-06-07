package com.river.entity;

import java.sql.Date;

public class RoomOperation {
    private int business;
    private String idnum;
    private int roomno;
    private Date enterdate;
    private Date outdate;
    private String status;

    public RoomOperation() {
    }

    public RoomOperation(int business, String idnum, int roomno, Date enterdate, Date outdate, String status) {
        this.business = business;
        this.idnum = idnum;
        this.roomno = roomno;
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

    public int getRoomno() {
        return roomno;
    }

    public void setRoomno(Integer roomnum) {
        this.roomno = roomnum;
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
                ", roomnum=" + roomno +
                ", enterdate=" + enterdate +
                ", outdate=" + outdate +
                ", status='" + status + '\'' +
                '}';
    }

}
