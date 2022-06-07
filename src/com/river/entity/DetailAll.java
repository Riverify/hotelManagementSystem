package com.river.entity;

import java.sql.Date;

public class DetailAll {
    private int business;
    private String name;
    private String phone;
    private String idnum;
    private int roomno;
    private Date enterdate;
    private long orderday;
    private String status;
    private String roomtype;
    private int bednum;
    private int price;

    public DetailAll() {
    }

    public DetailAll(int business, String name, String phone, String idnum, int roomno, Date enterdate, long orderday, String status, String roomtype, int bednum, int price) {
        this.business = business;
        this.name = name;
        this.phone = phone;
        this.idnum = idnum;
        this.roomno = roomno;
        this.enterdate = enterdate;
        this.orderday = orderday;
        this.status = status;
        this.roomtype = roomtype;
        this.bednum = bednum;
        this.price = price;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }

    public void setBusiness(Integer business) {
        this.business = business;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setRoomno(Integer roomno) {
        this.roomno = roomno;
    }

    public Date getEnterdate() {
        return enterdate;
    }

    public void setEnterdate(Date enterdate) {
        this.enterdate = enterdate;
    }

    public long getOrderday() {
        return orderday;
    }

    public void setOrderday(Long orderday) {
        this.orderday = orderday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "  流水号：" + business +
                "  房间号：" + roomno +
                "  入住时间：" + enterdate +
                "  姓名：" + name +
                "  订单状态：" + status +
                "  价格：" + price + "元/天" +
                "  住房时间：" + orderday + "天\n" + '\t' +
                "  电话：" + phone +
                "  身份证号：" + idnum +
                "  房屋类型：" + roomtype +
                "  床位数：" + bednum + "人间\n";

    }
}
