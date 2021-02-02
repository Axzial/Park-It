package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.constants.DBRequest;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

@Log4j
public class TicketDAO extends AbstractDAO<Ticket> {

    /**
     * Save a ticket
     * @param ticket {@link Ticket}
     * @return {@link Ticket}
     */
    public Ticket saveTicket(Ticket ticket) {
        try (Connection con = dataBaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(DBRequest.SAVE_TICKET.getRequest());
        ) {
            ps.setInt(1, ticket.getParkingSpot().getId());
            ps.setString(2, ticket.getVehicleRegNumber());
            ps.setDouble(3, ticket.getPrice());
            ps.setTimestamp(4, new Timestamp(ticket.getInTime().getTime()));
            ps.setTimestamp(5, (ticket.getOutTime() == null) ? null : (new Timestamp(ticket.getOutTime().getTime())));
            ps.execute();
            return ticket;
        } catch (Exception ex) {
            log.error("Error fetching next available slot", ex);
        }
        return ticket;
    }

    /**
     * Get a ticket by reg number
     * @param vehicleRegNumber
     * @return {@link Ticket}
     */
    public Ticket getTicket(String vehicleRegNumber) {
        try(Connection con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBRequest.GET_TICKET.getRequest());
        ) {
            ps.setString(1, vehicleRegNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt(1));
                ticket.setVehicleRegNumber(vehicleRegNumber);
                ticket.setPrice(rs.getDouble(4));
                ticket.setInTime(rs.getTimestamp(5));
                ticket.setOutTime(rs.getTimestamp(6));
                return ticket;
            }
            dataBaseConfig.closeResultSet(rs);
        } catch (Exception ex) {
            log.error("Error fetching next available slot", ex);
        }
        return new Ticket();
    }

    /**
     * Update a ticket in the database
     * @param ticket
     * @param <S>
     * @return
     */
    @Override
    public <S extends Ticket> S update(S ticket) {
        try(Connection con = dataBaseConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(DBRequest.UPDATE_TICKET.getRequest());
        ) {
            ps.setDouble(1, ticket.getPrice());
            ps.setTimestamp(2, new Timestamp(ticket.getOutTime().getTime()));
            ps.setInt(3, ticket.getId());
            ps.execute();
            return ticket;
        } catch (Exception ex) {
            log.error("Error saving ticket info", ex);
        }
        return ticket;
    }
}
