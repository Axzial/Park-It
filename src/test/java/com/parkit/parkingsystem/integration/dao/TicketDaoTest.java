package com.parkit.parkingsystem.integration.dao;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.Timestamp;
import java.time.Instant;

public class TicketDaoTest {

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

    @BeforeEach
    public void beforeEach(){

    }

    @Test
    public void testGetTicketFromRegNumber(){
        Ticket ticket = ticketDAO.getTicket("AX-ZI-AL");
        Assertions.assertEquals(1.22, ticket.getPrice());
    }

}