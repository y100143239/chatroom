package cn.pistachio.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alex on 2017/6/9.
 */
public class JDBCUtils {

    private static ComboPooledDataSource dataSource =
            new ComboPooledDataSource();

    //get connection pool
    public static DataSource getDataSource() {
        return dataSource;
    }

    //get connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
