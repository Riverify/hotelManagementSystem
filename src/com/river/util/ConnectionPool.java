package com.river.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * 连接池
 */
public class ConnectionPool {
    /**
     * 存放多个数据库连接
     */
    private static final LinkedList<Connection> list = new LinkedList<Connection>();

    /**
     * 第一次加载２类的时候
     */
    static {
        Connection conn = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/mydb?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String user = "com/river";
        String password = "20020126";

        try {
            // 加载驱动
            Class.forName(driver);

            // 建立数据库连接
            for (int i = 0; i < 10; i++) {
                conn = newConnection();
                list.addLast(conn);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * 创建一个新的连接
     *
     * @return 新的连接
     */
    public static Connection newConnection() {
        String url = "jdbc:mysql://127.0.0.1:3306/mydb?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
        String user = "com/river";
        String password = "20020126";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 从连接池中获取连接
     */
    public static Connection getConnection() {
        if (list.size() > 0) {
            return list.removeFirst();
        } else {
            return newConnection();
        }
    }

    /**
     * 数据库连接完毕,放回连接
     */
    public static void returnConnection(Connection conn) {
        if (list.size() < 10) {
            list.addLast(conn);
        } else {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
        System.out.println(getConnection());
        System.out.println(getConnection());
        System.out.println(getConnection());

    }
}
