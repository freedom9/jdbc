package com.freedom.dao;

import com.freedom.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author freedom
 * @date 2020/2/15 14:43
 * @description
 */
public abstract class BaseDao<T> {

    private Class<T> clazz = null;

    {
        // 获取当前BaseDao的子类继承父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;

        // 获取父类的泛型参数
        Type[] typeArguments = paramType.getActualTypeArguments();
        // 获取第一次参数
        clazz = (Class<T>) typeArguments[0];
    }

    public int update(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }

    public T getInstance(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    public List<T> getForList(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
                    String culumnLabel = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(culumnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }

    public <E> E getValue(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }
        return null;
    }
}
