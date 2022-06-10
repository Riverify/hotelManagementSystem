package com.river.dao;

import com.river.entity.Customer;
import com.river.entity.DetailAll;

import java.util.List;


/**
 * 该视图只能做查询语句
 */
public interface DetailAllDao {

    /**
     * 查询所有细节信息
     *
     * @return 细节信息对象
     */
    List<DetailAll> findAll();

    List<DetailAll> findOne(String phone);

    List<DetailAll> findOneByBusiness(String business);

    /**
     * 查询所有在住或者预约中的流水信息
     *
     * @return 细节信息对象
     */
    List<DetailAll> findAllStillIn();

    /**
     * 查询某个账号在住或者预约中的流水信息
     *
     * @return 细节信息对象
     */
    List<DetailAll> findAllStillIn(String phone);

    /**
     * 根据现有订单
     */
    List<DetailAll> isStillIn(String phone);

    List<DetailAll> findByIdnum(Customer customer);
}
