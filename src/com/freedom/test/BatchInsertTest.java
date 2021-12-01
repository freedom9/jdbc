package com.freedom.test;

import com.freedom.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author freedom
 * @date 2020/1/30 17:39
 * @description
 */
public class BatchInsertTest {

    /**
     * 使用批量处理的条件：
     * 1、JDBC驱动版本需要5.1.13或以上
     * 2、mysql服务器默认是关闭批处理的，需要在url增加rewriteBatchedStatements=true开启批处理
     */
    @Test
    public void testBatchInsertOne() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();

            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);

            int count = 10000;
            for (int i = 1; i <= count; i++) {
                ps.setObject(1, "name_" + i);

                // “攒”sql
                ps.addBatch();

                if (i % 500 == 0 || i == (count - 1)) {
                    // 执行batch
                    ps.executeBatch();
                    // 清空batch
                    ps.clearBatch();
                }
            }
            System.out.println("花费时间为：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    @Test
    public void testBatchInsertTwo() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();

            // 设置不自动提交
            conn.setAutoCommit(false);

            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);

            int count = 100000;
            for (int i = 1; i <= count; i++) {
                ps.setObject(1, "name_" + i);

                // “攒”sql
                ps.addBatch();

                if (i % 500 == 0 || i == (count - 1)) {
                    // 执行batch
                    ps.executeBatch();
                    // 清空batch
                    ps.clearBatch();
                }
            }
            // 提交数据
            conn.commit();

            System.out.println("花费时间为：" + (System.currentTimeMillis() - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }
}