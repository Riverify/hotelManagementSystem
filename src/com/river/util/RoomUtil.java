package com.river.util;

import com.river.dao.CustomerDao;
import com.river.dao.DetailAllDao;
import com.river.dao.impl.CustomerDaoImpl;
import com.river.dao.impl.DetailAllDaoImpl;
import com.river.entity.DetailAll;

import java.util.List;

public abstract class RoomUtil {
    public static int changeMoney(String business) {
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list = detailAllDao.findOneByBusiness(business);
        String phone = list.get(0).getPhone();      // 获取账号
        long orderday = list.get(0).getOrderday();  // 天数
        int price = list.get(0).getPrice();         // 单价
        int money = (int) (orderday * price);       // 总价
        CustomerDao customerDao = new CustomerDaoImpl();

        // 还未住房 扣除固定违约金
        if (orderday <= 0) {
            customerDao.changeMoney(phone, -80);
            customerDao.changeMoney("admin", 80);
            return -80;
        }
        // 住房了
        else {
            customerDao.changeMoney(phone, -money);
            customerDao.changeMoney("admin", money);
            return -money;
        }
    }


    // 退还钱
    public static int refundMoney(String business) {
        DetailAllDao detailAllDao = new DetailAllDaoImpl();
        List<DetailAll> list = detailAllDao.findOneByBusiness(business);
        String phone = list.get(0).getPhone();      // 获取账号
        long orderday = list.get(0).getOrderday();  // 天数
        int price = list.get(0).getPrice();         // 单价
        int money = (int) (orderday * price);       // 总价
        CustomerDao customerDao = new CustomerDaoImpl();
        customerDao.changeMoney(phone, money);
        customerDao.changeMoney("admin", -money);
        return money;
    }
}
