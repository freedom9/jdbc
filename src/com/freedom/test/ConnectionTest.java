package com.freedom.test;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author freedom
 * @date 2020/1/29 17:10
 * @description
 */
public class ConnectionTest {

    @Test
    public void testConnectionOne() throws SQLException {
        Driver driver = new com.mysql.jdbc.Driver();

        String url = "jdbc:mysql://47.103.219.222:3306/test";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }


    /**
     * 相比于方式一，不需要引入第三方api,是的程序具有更好的可移植性
     * @throws Exception
     */
    @Test
    public void testConnectionTwo() throws Exception {
        // 使用反射获取Driver实现类对像
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://47.103.219.222:3306/test";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "123456");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    @Test
    public void testConnectionThree() throws Exception{
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://47.103.219.222:3306/test";
        String user = "root";
        String password = "123456";

        // 注册驱动
        DriverManager.registerDriver(driver);

        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

    /**
     * 相较于方式三，可以省略了注册驱动步骤，因为mysql的Driver实现类，做了以下的操作
     * static {
     *     try {
     *         DriverManager.registerDriver(new Driver());
     *     } catch (SQLException var1) {
     *         throw new RuntimeException("Can't register driver!");
     *     }
     * }
     *
     * @throws Exception
     */
    @Test
    public void testConnectionFour() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://47.103.219.222:3306/test";
        String user = "root";
        String password = "123456";

        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }

    /**
     * 推荐使用
     * 好处：
     * 1、实现数据与代码的分离，实现了解耦。
     * 2、修改配置文件，可以避免程序重新打包。
     * @throws Exception
     */
    @Test
    public void testConnectionFive() throws Exception {
        // 读取配置
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

        Properties properties = new Properties();
        properties.load(is);

        String driverClass = properties.getProperty("driverClass");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Class.forName(driverClass);

        Connection conn = DriverManager.getConnection(url, user, password);

        System.out.println(conn);
    }
}
