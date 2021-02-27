package com.parkit.parkingsystem.integration.config;

import com.parkit.parkingsystem.config.DataBaseConfig;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j
@ExtendWith(MockitoExtension.class)
public class DataBaseTestConfig extends DataBaseConfig {

    /**
     * Environment Variables for Database Ids
     */
    @Getter
    @Setter
    private String PASSWORD = System.getenv("MYSQL_PASSWORD");
    @Getter
    @Setter
    private String USERNAME = System.getenv("MYSQL_USER");

    /**
     * Get the main connection
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        log.info("Create DB connection");
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/park_it_test", "root", "apqmwn1A");
    }

    /**
     * Close the connection
     * @param con
     */
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

    /**
     * Close the result set
     * @param rs {@link ResultSet}
     */
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
