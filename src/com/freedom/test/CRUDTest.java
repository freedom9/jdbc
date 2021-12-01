package com.freedom.test;

import com.freedom.bean.Customer;
import com.freedom.bean.Order;
import com.freedom.utils.JDBCUtils;
import org.junit.Test;

import java.util.List;

/**
 * @author freedom
 * @date 2020/1/29 21:15
 * @description
 */
public class CRUDTest {

    @Test
    public void testGetInstance() {
        String customerSql = "select id, name, email from customers where id = ?";
        Customer customer = JDBCUtils.getInstance(Customer.class, customerSql, 6);
        System.out.println(customer);
        System.out.println("--------------------------------");

        String orderSql = "select order_id as orderId, order_name as orderName, order_date as orderDate from `order` where order_name = ?";
        Order order = JDBCUtils.getInstance(Order.class, orderSql, "AA");
        System.out.println(order);
    }

    @Test
    public void testGetForList(){

        String customerSql = "select id,name,email from customers where id < ?";
        List<Customer> list = JDBCUtils.getForList(Customer.class,customerSql,12);
        list.forEach(System.out::println);
        System.out.println("-----------------------------");

        String orderSql = "select order_id orderId,order_name orderName from `order`";
        List<Order> orderList =JDBCUtils.getForList(Order.class, orderSql);
        orderList.forEach(System.out::println);
    }

    @Test
    public void update() {
        String insertSql = "insert into customers (name, email, birth) values (?, ?, ?)";
        int insertResult = JDBCUtils.update(insertSql, "李白", "libai@163.com", "1200-01-01");
        if (insertResult > 0) {
            System.out.println("增加成功");
        } else {
            System.out.println("增加失败");
        }
        System.out.println("-------------------------");

        String updateSql = "update `order` set order_name = ? where order_id = ?";
        int updateResult = JDBCUtils.update(updateSql, "DD", 1);
        if (insertResult > 0) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
        System.out.println("-------------------------");

        String deleteSql = "delete from customers where id = ?";
        int deleteResult = JDBCUtils.update(deleteSql, 18);
        if (insertResult > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }
}
