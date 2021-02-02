package com.parkit.parkingsystem.integration.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
public class DataBaseTestConfig extends DataBaseConfig {

    /**
     * Environment Variables for Database Ids
     */
    String PASSWORD = System.getenv("MYSQL_PASSWORD");
    String USERNAME = System.getenv("MYSQL_USER");

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        log.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/park_it", USERNAME, PASSWORD);
    }

    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
                log.info("Closing DB connection");
            } catch (SQLException e) {
                log.error("Error while closing connection",e);
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
                log.info("Closing Result Set");
            } catch (SQLException e) {
                log.error("Error while closing result set",e);
            }
        }
    }
}
