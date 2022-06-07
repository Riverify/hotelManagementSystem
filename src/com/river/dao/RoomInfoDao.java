package com.river.dao;

import com.river.entity.RoomInfo;

import java.util.List;

public interface RoomInfoDao {

    /**
     * 查找空余房间
     *
     * @param roomInfo 房间对象
     * @return 房间为占用的房间对象的集合
     */
    List<RoomInfo> selectEmptyRoom(RoomInfo roomInfo);
}
