package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

public class ParkingDaoTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;

    @BeforeAll
    public static void beforeAll(){
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        Ticket ticket = new Ticket();
        ticket.setId(7);
        ticket.setVehicleRegNumber("AX-ZI-AL");
        ticket.setPrice(1.22);
        ticket.setInTime(Timestamp.from(Instant.now()));
        ticket.setOutTime(Timestamp.from(Instant.now().plusSeconds(86985)));
        ticket.setParkingSpot(new ParkingSpot(1, ParkingType.CAR, true));
        ticketDAO.saveTicket(ticket);
    }

    @Test
    public void getNextParkingSpot(){
        Assertions.assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }

    @Test
    public void updateParkingSpot(){
        ParkingSpot spot = parkingSpotDAO.update(new ParkingSpot(1, ParkingType.CAR, false));
        Assertions.assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }

}
