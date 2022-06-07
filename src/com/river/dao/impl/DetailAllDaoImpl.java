package com.river.dao.impl;

import com.river.dao.DetailAllDao;
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
}