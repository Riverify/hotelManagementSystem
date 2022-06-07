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
}
