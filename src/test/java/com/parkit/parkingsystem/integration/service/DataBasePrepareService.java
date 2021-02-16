package com.parkit.parkingsystem.integration.service;

import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;

public class DataBasePrepareService {

    @Getter
    @Setter
    private DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();

    /**
     * Clean all database entries
     */
    public void clearDataBaseEntries(){
        Connection connection = null;
        try{
            connection = dataBaseTestConfig.getConnection();

            //set parking entries to available
            connection.prepareStatement("update parking set available = true").execute();

            //clear ticket entries;
            connection.prepareStatement("truncate table ticket").execute();

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            dataBaseTestConfig.closeConnection(connection);
        }
    }


}
