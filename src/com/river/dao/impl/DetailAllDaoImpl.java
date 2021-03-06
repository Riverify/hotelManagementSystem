package com.river.dao.impl;

import com.river.dao.DetailAllDao;
import com.river.entity.Customer;
import com.river.entity.DetailAll;
import com.river.util.DBUtil;

import java.util.List;


public class DetailAllDaoImpl implements DetailAllDao {
    @Override
    public List<DetailAll> findAll() {
        String sql = "select * from detailAll";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }

    @Override
    public List<DetailAll> findOne(String phone) {
        String sql = "select * from detailAll where phone = ?";
        Object[] params = {phone};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }

    @Override
    public List<DetailAll> findOneByBusiness(String business) {
        String sql = "select * from detailAll where business = ?";
        Object[] params = {business};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }

    @Override
    public List<DetailAll> findAllStillIn() {
        String sql = "select * from detailAll where status = '预定' or status = '在住'";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }

    @Override
    public List<DetailAll> findAllStillIn(String phone) {
        String sql = "select * from detailAll where status = '预定' or status = '在住' and phone = ?";
        Object[] params = {phone};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }

    @Override
    public List<DetailAll> isStillIn(String phone) {
        String sql = "select roomno from detailAll where status = '预定' or status = '在住' and phone = ?";
        Object[] params = {phone};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }

    @Override
    public List<DetailAll> findByIdnum(Customer customer) {
        String sql = "select * from detailAll where status = '预定' or status = '在住' and idnum = ?";
        Object[] params = {customer.getIdnum()};
        return DBUtil.executeQuery(sql, params, DetailAll.class);
    }
}
