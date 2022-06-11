package com.river.dao.impl;

import com.river.dao.RoomInfoDao;
import com.river.entity.RoomInfo;
import com.river.util.DBUtil;

import java.util.List;

public class RoomInfoDaoImpl implements RoomInfoDao {
    @Override
    public List<RoomInfo> selectEmptyRoom() {
        String sql = "select * from roomInfo where booked = false";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, RoomInfo.class);
    }

    @Override
    public List<RoomInfo> selectNotEmptyRoom(RoomInfo roomInfo) {
        String sql = "select roomno from roomInfo where booked = true";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, RoomInfo.class);
    }


    @Override
    public int makeRoomEmpty(int roomno) {
        String sql = "update roomInfo set booked = false where roomno = ?";
        Object[] params = {roomno};
        return DBUtil.executeUpdate(sql, params);
    }

    @Override
    public int makeRoomOccupy(int roomno) {
        String sql = "update roomInfo set booked = true where roomno = ?";
        Object[] params = {roomno};
        return DBUtil.executeUpdate(sql, params);
    }
}
