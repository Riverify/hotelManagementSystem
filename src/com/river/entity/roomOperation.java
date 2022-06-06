package com.river.entity;

import java.util.Date;

public class roomOperation {
    private int business;
    private String idnum;
    private int roomnum;
    private Date enterDate;
    private Date outDate;
    private String status;

    public roomOperation() {
    }

    public roomOperation(int business, String idnum, int roomnum, Date enterDate, Date outDate, String status) {
        this.business = business;
        this.idnum = idnum;
        this.roomnum = roomnum;
        this.enterDate = enterDate;
        this.outDate = outDate;
        this.status = status;
    }

    public int getBussiness() {
        return business;
    }

    public void setBussiness(int business) {
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

    public void setRoomnum(int roomnum) {
        this.roomnum = roomnum;
    }

    public Date getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(Date enterDate) {
        this.enterDate = enterDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
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
                ", enterDate=" + enterDate +
                ", outDate=" + outDate +
                ", status='" + status + '\'' +
                '}';
    }

}
