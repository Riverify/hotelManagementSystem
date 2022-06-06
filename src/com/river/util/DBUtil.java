package com.river.util;

import com.river.entity.Employee;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 数据库工具类
 * 避免代码重复
 */
public abstract class DBUtil {
    // 定义一个日志记录器
    private static final Logger logger = Logger.getLogger(DBUtil.class.getName());

    // 在合适的位置使用日志

    private DBUtil() {
    }

    static Connection conn = null;
    static String driver = "";
    static String url = "";
    static String user = "";
    static String password = "";


    static {
        // 创建Properties对象
        Properties prop = new Properties();

        // 读取属性文件
        InputStream is = DBUtil.class.getResourceAsStream("/jdbc.properties");
        try {
            prop.load(is);
            logger.info("正确的读取了jdbc.properties" + prop);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("读取jdbc.properties出错" + e);
        }

        // 通过key获取values;
        driver = prop.getProperty("driver");
        url = prop.getProperty("url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");

    }

    /**
     * 获取数据库的连接
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 建立数据库连接
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭所有资源
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DML Update insert delete
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int n = 0; // 添加失败

        // 加载驱动
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            n = pstmt.executeUpdate();

            // 处理结果
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return n;
    }

    public static <T> List<T> executeQuery(String sql, Object[] params, Class clazz) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();

        // 加载驱动
        try {
            conn = DBUtil.getConnection();
            // 创建sql命令发射器
            pstmt = conn.prepareStatement(sql);

            // 准备好sql语句，通过sql命令发射器发送给数据库，并得到结果
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int count = rsmd.getColumnCount();

            // 处理结果
            while (rs.next()) {
                T entity = (T) clazz.newInstance();

                for (int i = 0; i < count; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object value = rs.getObject(columnName);
                    String methodName =
                            "set" + columnName.substring(0, 1).toUpperCase()
                                    + columnName.substring(1).toLowerCase();
                    Class parameterTypes = Class.forName(rsmd.getColumnClassName(i + 1));
                    Method method = clazz.getMethod(methodName, parameterTypes);
                    method.invoke(entity, value);
                }
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            // 关闭资源
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return list;
    }

    public static List<Employee> executeQuery2(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Employee> list = new ArrayList<>();

        // 加载驱动
        try {
            conn = DBUtil.getConnection();
            // 创建sql命令发射器
            pstmt = conn.prepareStatement(sql);

            // 准备好sql语句，通过sql命令发射器发送给数据库，并得到结果
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            rs = pstmt.executeQuery();

            // 处理结果
            while (rs.next()) {
                Employee emp = new Employee();

                int empno = rs.getInt("empno");
                emp.setEmpno(empno);

                String ename = rs.getString("ename");
                emp.setEname(ename);

                String job = rs.getString("job");
                emp.setJob(job);

                int mgr = rs.getInt("mgr");
                emp.setMgr(mgr);

                Date hireDate = rs.getDate("hiredate");
                emp.setHireDate(hireDate);

                double sal = rs.getDouble("sal");
                emp.setSal(sal);

                double comm = rs.getDouble("comm");
                emp.setComm(comm);

                int depton = rs.getInt("deptno");
                emp.setDeptno(depton);

                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            DBUtil.closeAll(rs, pstmt, conn);
        }
        return list;
    }
}
