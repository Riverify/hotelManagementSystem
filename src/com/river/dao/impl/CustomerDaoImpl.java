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
    public List<Customer> checkRegister(Customer customer) {
        String sql = "select * from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public List<Customer> findOne(Customer customer) {
        String sql = "select * from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

    @Override
    public List<Customer> findIdnumByPhone(Customer customer) {
        String sql = "select idnum from customer where phone = ?";
        Object[] params = {customer.getPhone()};
        return DBUtil.executeQuery(sql, params, Customer.class);
    }

}
