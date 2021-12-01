package com.freedom.test;

import com.freedom.bean.Customer;
import com.freedom.dao.CustomerDao;
import com.freedom.dao.CustomerDaoImpl;
import com.freedom.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @author freedom
 * @date 2020/2/15 20:15
 * @description
 */
public class CustomerTest {

    private CustomerDao dao = new CustomerDaoImpl();

    @Test
    public void testCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            System.out.println("表的总记录：" + dao.getCount(conn));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testQueryList() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            List<Customer> customerList = dao.getQueryList(conn);

            customerList.forEach(System.out :: println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testQueryObject(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            System.out.println(dao.getQueryObject(conn, 6));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Customer customer = new Customer(30, "古天乐", "gutianle@163.com", new Date(5423625656L));
            dao.insert(conn, customer);
            System.out.println("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testUpdate() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            Customer customer = new Customer(30, "古天乐", "gutianle@163.com", new Date(454846857876L));
            dao.update(conn, customer);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }

    @Test
    public void testDelete() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            dao.delete(conn, 30);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, null);
        }
    }
}
