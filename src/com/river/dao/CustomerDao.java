package com.river.dao;

import com.river.entity.Customer;

import java.util.List;

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
    List<Customer> checkLogin(Customer customer);

    /**
     * 注册检查
     *
     * @param customer 用户类
     * @return 影响行数
     */
    List<Customer> checkRegister(Customer customer);

    /**
     * 查找所有客户
     *
     * @return
     */
    List<Customer> findAll();

    /**
     * 根据手机号查找特定用户
     *
     * @param customer
     * @return
     */
    List<Customer> findOne(Customer customer);

    /**
     * 根据手机号获得身份证信息
     */
    List<Customer> findIdnumByPhone(Customer customer);

    List<Customer> findNameByPhone(Customer customer);

    List<Customer> findNotVip();

    List<Customer> findVip();

    int passVip(String phone);

    int passVipAll();
}
