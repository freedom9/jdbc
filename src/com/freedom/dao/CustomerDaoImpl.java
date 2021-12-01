package com.freedom.dao;

import com.freedom.bean.Customer;

import java.sql.Connection;
import java.util.List;

/**
 * @author freedom
 * @date 2020/2/15 19:55
 * @description
 */
public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao {

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customers";
        return getValue(conn, sql);
    }

    @Override
    public List<Customer> getQueryList(Connection conn) {
        String sql = "select id, name, email, birth from customers";
        return getForList(conn, sql);
    }

    @Override
    public Customer getQueryObject(Connection conn, Integer id) {
        String sql = "select id, name, email, birth from customers where id = ?";
        return getInstance(conn, sql, id);
    }

    @Override
    public void insert(Connection conn, Customer customer) {
        String sql = "insert into customers(id, name, email, birth) values (?, ?, ?, ?)";
        update(conn, sql, customer.getId(), customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void update(Connection conn, Customer customer) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";
        update(conn, sql, customer.getName(), customer.getEmail(), customer.getBirth(), customer.getId());
    }

    @Override
    public void delete(Connection conn, Integer id) {
        String sql = "delete from customers where id = ?";
        update(conn, sql, id);
    }
}
