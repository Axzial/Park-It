package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.Instant;

public class TicketDaoTest {

    private static final DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static TicketDAO ticketDAO;

    @BeforeAll
    public static void beforeAll(){
        DataBasePrepareService.clearDataBaseEntries();

        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        ParkingSpotDAO parkingSpotDAO = new ParkingSpotDAO();
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

    @BeforeEach
    public void beforeEach(){

    }

    @Test
    public void testGetTicketFromRegNumber(){
        Ticket ticket = ticketDAO.getTicket("AX-ZI-AL");
        Assertions.assertEquals(1.22, ticket.getPrice());
    }

    @Test
    public void countVistsTest(){
        Assertions.assertEquals(1, ticketDAO.countVisits("AX-ZI-AL"));
    }

    @Test
    public void updateTicketTest(){
        Ticket ticketBefore = ticketDAO.getTicket("AX-ZI-AL");
        ticketBefore.setVehicleRegNumber("CHANGED");
        ticketDAO.update(ticketBefore);
        Ticket ticketAfter = ticketDAO.getTicket("CHANGED");
        Assertions.assertNotNull(ticketAfter);
    }

}
