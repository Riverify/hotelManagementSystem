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

    /**
     * 查询所有在住或者预约中的流水信息
     *
     * @return 细节信息对象
     */
    List<DetailAll> findAllStillIn();

    List<DetailAll> findByIdnum(Customer customer);
}
