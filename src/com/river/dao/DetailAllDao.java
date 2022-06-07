package com.river.dao;

import com.river.entity.DetailAll;

import java.util.List;


/**
 * 该视图只能做查询语句
 */
public interface DetailAllDao {

    /**
     * 查询所有细节信息
     *
     * @return
     */
    List<DetailAll> findAll();
}
