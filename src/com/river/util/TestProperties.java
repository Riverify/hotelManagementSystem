package com.river.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 一种map，存储键值对
 * 建议使用getProperty方法
 */
public class TestProperties {
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
//        prop.put(1, new Date().toString());
//        System.out.println(prop.get(1));
//        prop.setProperty("driver", "com.mysql.cj.jdbc.Driver");
//        System.out.println(prop.getProperty("driver"));

        // 读取项目中的属性文件
        InputStream is = TestProperties.class.getResourceAsStream("/jdbc.properties");
        prop.load(is);
        System.out.println(prop.getProperty("driver"));
    }
}
