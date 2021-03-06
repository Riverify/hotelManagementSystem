package com.river.entity;

import com.river.dao.DetailAllDao;
import com.river.dao.impl.DetailAllDaoImpl;

import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String idnum;
    private String phone;
    private boolean vip;
    private int money;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public Customer() {
    }

    public Customer(int id, String name, String idnum, String phone, boolean vip, int money, String password) {
        this.id = id;
        this.name = name;
        this.idnum = idnum;
        this.phone = phone;
        this.vip = vip;
        this.money = money;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVip() {
        return vip;
    }

    public boolean getVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }


    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idnum='" + idnum + '\'' +
                ", phone='" + phone + '\'' +
                ", vip=" + vip +
                ", money=" + money +
                ", password='" + password + '\'' +
                '}';
    }

    // ?????????toString???????????????????????????
    public String showInfo(Customer customer) {
        // ????????????????????????????????????????????????
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list = detailAllDao.isStillIn(customer.getPhone());

        StringBuilder s = new StringBuilder("?????????" + name + '\n' +
                "????????????" + phone + '\n' +
                "?????????" + money + "???\n" +
                "??????????????????" + idnum + '\n' +
                "vip???" + (vip ? "???" : "???") + '\n' +
                "?????????????????????");

        for (DetailAll detailAll : list) {
            s.append(detailAll.getRoomno()).append(" ");
        }

        s.append("\n");
        return s.toString();
    }

    public String showMoreInfo(Customer customer) {
        // ????????????????????????????????????????????????
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list = detailAllDao.isStillIn(customer.getPhone());

        StringBuilder s = new StringBuilder("?????????" + name + '\n' +
                "????????????" + phone + '\n' +
                "?????????" + money + "???\n" +
                "??????????????????" + idnum + '\n' +
                "vip???" + (vip ? "???" : "???") + '\n' +
                "?????????" + password + '\n' +
                "?????????????????????");

        for (DetailAll detailAll : list) {
            s.append(detailAll.getRoomno()).append(" ");
        }
        s.append("\n");
        return s.toString();
    }
}
