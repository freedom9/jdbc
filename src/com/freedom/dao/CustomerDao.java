package com.freedom.dao;

import com.freedom.bean.Customer;

import java.sql.Connection;
import java.util.List;

/**
 * @author freedom
 * @date 2020/2/15 19:55
 * @description
 */
public interface CustomerDao {

    Long getCount(Connection conn);

    List<Customer> getQueryList(Connection conn);

    Customer getQueryObject(Connection conn, Integer id);

    void insert(Connection conn, Customer customer);

    void update(Connection conn, Customer customer);

    void delete(Connection conn, Integer id);
}
