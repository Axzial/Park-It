package com.parkit.parkingsystem.config;

import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

@Log4j
public class DataBaseConfig {

    /**
     * Environment Variables for Database Ids
     */
    String PASSWORD = System.getenv("MYSQL_PASSWORD");
    String USERNAME = System.getenv("MYSQL_USER");

    /**
     * Open a new {@link Connection}
     * @return a {@link Connection}
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/park_it", USERNAME, PASSWORD);
    }

    /**
     * Close a {@link Connection}
     * @param con {@link Connection}
     */
    public void closeConnection(Connection con){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                log.error("Error while closing connection", e);
            }
        }
    }

    /**
     * Close a {@link PreparedStatement}
     * @param ps {@link PreparedStatement}
     */
    public void closePreparedStatement(PreparedStatement ps) {
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                log.error("Error while closing prepared statement",e);
            }
        }
    }

    /**
     * Close a {@link ResultSet}
     * @param rs {@link ResultSet}
     */
    public void closeResultSet(ResultSet rs) {
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Error while closing result set",e);
            }
        }
    }
}
