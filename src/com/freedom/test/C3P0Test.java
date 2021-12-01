package com.freedom.test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.sql.Connection;

/**
 * @author freedom
 * @date 2020/2/12 15:58
 * @description
 */
public class C3P0Test {

    @Test
    public void testGetConnectionOne() throws Exception {
        // 获取c3p0的连接池
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setDriverClass("com.mysql.jdbc.Driver");
        source.setJdbcUrl("jdbc:mysql://47.103.219.222:3306/test");
        source.setUser("root");
        source.setPassword("123456");

        source.setInitialPoolSize(10);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetConnectionTwo() throws Exception {
        ComboPooledDataSource source = new ComboPooledDataSource("test");
        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
