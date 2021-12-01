package com.freedom.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author freedom
 * @date 2020/1/29 18:22
 * @description
 */
public class JDBCUtils {

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(is);

        String driverClass = properties.getProperty("driverClass");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Class.forName(driverClass);

        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    /**
     * 使用druid连接池创建连接
     */
    private static DataSource source = null;

    static {
        try {
            Properties pros = new Properties();
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");
            pros.load(is);

            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getDuridConnection() throws Exception {
        Connection conn = source.getConnection();
        return conn;
    }

    /**
     * 关闭Statement和数据库连接资源
     *
     * @param ps
     * @param conn
     */
    public static void closeResource(Connection conn, Statement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭ResultSet、Statement和数据库连接资源
     *
     * @param conn
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询单条记录的通用操作
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集的每一列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值：通过ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    // 获取列的列名：getColumnName()，通过ResultSetMetaData
                    // 获取列的别名：getColumnLabel()，通过ResultSetMetaData ------->推荐使用
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    // 通过反射，将对象指定名columnLabel的属性赋值为指定的值columnValue
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn, ps, rs);
        }
        return null;
    }

    /**
     * 查询多条记录的通用查询
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public static <T> List<T> getForList(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();

            List<T> list = new ArrayList<>();

            while (rs.next()) {
                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn, ps, rs);
        }
        return null;
    }

    /**
     * 通用增删改操作
     *
     * @param sql
     * @param args
     * @return
     */
    public static int update(String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(conn, ps);
        }
        return 0;
    }
}
