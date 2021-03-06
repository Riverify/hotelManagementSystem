package com.river.dao;

import com.river.entity.RoomInfo;

import java.util.List;

public interface RoomInfoDao {

    /**
     * 查找空余房间
     *
     * @return 房间为占用的房间对象的集合
     */
    List<RoomInfo> selectEmptyRoom();

    /**
     * 查找非空余房间
     *
     * @param roomInfo 房间对象
     * @return 房间为占用的房间对象的集合
     */
    List<RoomInfo> selectNotEmptyRoom(RoomInfo roomInfo);

    /**
     * 将房间释放
     *
     * @param roomno
     * @return
     */

    // 不考虑流水号时
    int makeRoomEmpty(int roomno);

    /**
     * 将房间占有
     *
     * @param roomno
     * @return
     */
    int makeRoomOccupy(int roomno);


}
