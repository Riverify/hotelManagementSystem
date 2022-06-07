package com.river.dao.impl;

import com.river.dao.RoomOperationDao;
import com.river.entity.RoomOperation;
import com.river.util.DBUtil;

import java.util.List;

public class RoomOperationImpl implements RoomOperationDao {
    @Override
    public int update(RoomOperation r) {
        String sql = "update roomOperation set roomno = ?, enterdate = ?, outdate = ? where business = ?";
        Object[] params = {r.getRoomno(), r.getEnterdate(), r.getOutdate(), r.getBusiness()};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<RoomOperation> selcetBusiness(RoomOperation r) {
        String sql = "select business from roomOperation where enterdate < outdate and enterdate > CURRENT_DATE and outdate >= CURRENT_DATE";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, RoomOperation.class);
    }

    @Override
    public List<RoomOperation> selcetRoomnoByBusiness(RoomOperation r) {
        String sql = "select roomno from roomOperation where business = ?";
        Object[] params = {r.getBusiness()};
        return DBUtil.executeQuery(sql, params, RoomOperation.class);
    }

    @Override
    public int changeRoom(int beforeRoom, int afterRoom, int business) {
        String sql = "update roomOperation set roomno = ? where roomno = ? and business = ?";
        Object[] params = {afterRoom, beforeRoom, business};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public List<RoomOperation> autoExit() {
        String sql = "select roomno from roomOperation where outdate < CURRENT_DATE()";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, RoomOperation.class);
    }

    @Override
    public int outRoom(int roomno) {
        String sql = "update roomOperation set outdate = CURRENT_DATE() where roomno = ?";
        Object[] params = {roomno};
        return DBUtil.executeUpdate(sql, params);
    }
}
