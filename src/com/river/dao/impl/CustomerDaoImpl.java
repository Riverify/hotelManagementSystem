package com.river.dao.impl;

import com.river.dao.CustomerDao;
import com.river.entity.Customer;
import com.river.util.DBUtil;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public int register(Customer customer) {
        String sql = "insert into customer values(null, ?, ?, ?, ?, ?)";
        Object[] params = {customer.getName(), customer.getIdnum(),
                customer.getPhone(), customer.getVip(), customer.getMoney()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int checkLogin(Customer customer) {
        String sql = "select * from customer where phone = ? and password = ?";
        Object[] params = {customer.getPhone(), customer.getPassword()};
        return DBUtil.executeUpdate(sql, params);
    }


}
