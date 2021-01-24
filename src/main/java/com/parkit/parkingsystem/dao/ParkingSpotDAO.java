package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.DBRequest;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Log4j
public class ParkingSpotDAO extends AbstractDAO<ParkingSpot> {

    public int getNextAvailableSlot(ParkingType parkingType) {
        int result = -1;
        try (Connection con = dataBaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(DBRequest.GET_NEXT_PARKING_SPOT.getRequest());
        ) {
            ps.setString(1, parkingType.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            log.error("Error fetching next available slot", ex);
        }
        return result;
    }

    @Override
    public <S extends ParkingSpot> S update(S parkingSpot) {
        try (Connection con = dataBaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(DBRequest.UPDATE_PARKING_SPOT.getRequest());
        ) {
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            dataBaseConfig.closePreparedStatement(ps);
            return parkingSpot;
        } catch (Exception ex) {
            log.error("Error updating parking info", ex);
        }
        return parkingSpot;
    }
}
