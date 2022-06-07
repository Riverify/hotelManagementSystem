package com.river.dao.impl;

import com.river.dao.RoomOperationDao;
import com.river.entity.RoomOperation;
import com.river.util.DBUtil;

import java.util.List;

public class RoomOperationImpl implements RoomOperationDao {
    @Override
    public int update(RoomOperation r) {
        String sql = "update roomOperation set roomno = ?, enterdate = ?, outdate = ? where business = ?";
        Object[] params = {r.getRoomnum(), r.getEnterdate(), r.getOutdate(), r.getBusiness()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<RoomOperation> selcetBusiness(RoomOperation r) {
        String sql = "select business from roomOperation where outdate < CURRENT_DATE()";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, RoomOperation.class);
    }
}
