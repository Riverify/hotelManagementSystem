package com.river.dao.impl;

import com.river.dao.CustomerDao;
import com.river.entity.Customer;
import com.river.util.DBUtil;

import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public int register(Customer customer) {
        // vip非空，默认为false
        String sql = "insert into customer values(null, ?, ?, ?, false, ?, ?)";
        Object[] params = {customer.getName(), customer.getIdnum(),
                customer.getPhone(), customer.getMoney(), customer.getPassword()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<Customer> checkLogin(Customer customer) {
        String sql = "select * from customer where phone = ? and password = ?";
        Object[] params = {customer.getPhone(), customer.getPassword()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> checkLogin2(Customer customer) {
        String sql = "select * from customer where phone = ? and password = ? and vip = true";
        Object[] params = {customer.getPhone(), customer.getPassword()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> checkRegister(Customer customer) {
        String sql = "select * from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findOne(Customer customer) {
        String sql = "select * from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }


    @Override
    public List<Customer> getAdminPassword() {
        String sql = "select password from customer where id = 1";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findIdnumByPhone(Customer customer) {
        String sql = "select idnum from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findNameByPhone(Customer customer) {
        String sql = "select name from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findNotVip() {
        String sql = "select * from customer where vip = false";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findVip() {
        String sql = "select * from customer where vip = true";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public int passVip(String phone) {
        String sql = "update customer set vip = true where phone = ?";
        Object[] params = {phone};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int passVipAll() {
        String sql = "update customer set vip = true where vip = false";
        Object[] params = {};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int changePassword(String phone, String newPassword) {
        String sql = "update customer set password = ? where phone = ?";
        Object[] params = {newPassword, phone};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int changeMoney(String phone, long money) {
        String sql = "update customer set money = money + ? where phone = ?";
        Object[] params = {money, phone};
        return DBUtil.executeUpdate(sql, params);
    }

}
