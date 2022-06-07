package com.river.dao;

import com.river.entity.RoomOperation;

import java.util.List;

public interface RoomOperationDao {
    int update(RoomOperation r);

    /**
     * 获得当前正在进行中的订单的订单号
     *
     * @param r 房间订单对象
     * @return 带有当前正在进行中的订单的订单流水号的房间订单对象集合
     */
    List<RoomOperation> selcetBusiness(RoomOperation r);

    /**
     * 通过订单流水获得房间号
     */
    List<RoomOperation> selcetRoomnoByBusiness(RoomOperation r);

    int changeRoom(int beforeRoom, int afterRoom, int business);


    /**
     * 自动检测退房
     *
     * @return
     */
    List<RoomOperation> autoExit();

    int outRoom(int roomno);
}
