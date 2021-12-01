package com.freedom.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.freedom.utils.JDBCUtils;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @author freedom
 * @date 2020/2/12 17:34
 * @description
 */
public class DruidTest {

    @Test
    public void testGetConnectionOne() throws Exception {
        DruidDataSource source = new DruidDataSource();
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://47.103.219.222/test");
        source.setUsername("root");
        source.setPassword("123456");

        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetConnectionTwo() throws Exception {
        Properties pros = new Properties();
        FileInputStream is = new FileInputStream(new File("src/druid.properties"));
        pros.load(is);

        DataSource source = DruidDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    @Test
    public void testGetConnectionThree() throws Exception {
        System.out.println(JDBCUtils.getDuridConnection());
    }
}
