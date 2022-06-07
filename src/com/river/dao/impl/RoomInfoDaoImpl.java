package com.river.dao.impl;

import com.river.dao.RoomInfoDao;
import com.river.entity.RoomInfo;
import com.river.util.DBUtil;

import java.util.List;

public class RoomInfoDaoImpl implements RoomInfoDao {
    @Override
    public List<RoomInfo> selectEmptyRoom(RoomInfo roomInfo) {
        String sql = "select roomno from roomInfo where booked = false";
        Object[] params = {};
        return DBUtil.executeQuery(sql, params, RoomInfo.class);
    }
}
