package com.atming.utils;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @author annoming
 * @date 2021/3/25 7:50 上午
 */

public class DbUtil {

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    private static void getConnection() throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        InputStream is = DbUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        properties.load(is);
        String driver = properties.getProperty("jdbc.driverClassName");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        Class.forName(driver);
        connection = DriverManager.getConnection(url,username,password);
    }

    public static String queryData(String sql){
        String data = null;
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                data = resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
