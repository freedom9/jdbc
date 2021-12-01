package com.freedom.test;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author freedom
 * @date 2020/2/12 16:53
 * @description
 */
public class DBCPTest {

    /**
     * 使用dbcp连接池，需要导入commons-dbcp.jar和commons-pool.jar
     */
    @Test
    public void testGetConnectionOne() throws Exception {
        // 获取dbcp连接池
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://47.103.219.222:3306/test");
        source.setUsername("root");
        source.setPassword("123456");

        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetConnectionTwo() throws Exception{
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        pros.load(is);

        DataSource source = BasicDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
