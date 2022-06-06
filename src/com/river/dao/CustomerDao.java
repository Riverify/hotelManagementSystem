package com.river.dao;

import com.river.entity.Customer;

public interface CustomerDao {
    /**
     * 注册信息
     *
     * @param customer 用户类
     * @return 影响行数
     */
    int register(Customer customer);

    /**
     * 登陆检查
     *
     * @param customer 用户类
     * @return 影响行数
     */
    int checkLogin(Customer customer);
}
