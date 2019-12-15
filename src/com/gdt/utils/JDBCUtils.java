package com.gdt.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtils {

    //1.声明一个DataSource
    private static DataSource dataSource = new ComboPooledDataSource();

    //2.提供一个返回DataSource的方法
    public static DataSource getDataSource(){
        return dataSource;
    }

    //3.提供一个返回Connection连接的方法
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
