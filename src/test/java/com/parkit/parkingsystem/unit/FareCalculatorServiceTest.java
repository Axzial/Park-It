package com.parkit.parkingsystem.unit;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.FareCalculatorService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FareCalculatorServiceTest {

    @Mock
    private TicketDAO ticketDAO;
    private static FareCalculatorService fareCalculatorService;
    private Ticket ticket;

    @BeforeAll
    private static void setUp() {
        DataBasePrepareService.clearDataBaseEntries();
        fareCalculatorService = new FareCalculatorService();
    }

    @BeforeEach
    private void setUpPerTest() {
        ticket = new Ticket();
    }

    @Test
    public void calculateFareCar() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(ticket.getPrice(), Fare.CAR_RATE_PER_HOUR.getPriceHour());
    }

    @Test
    public void calculateFareBike() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(ticket.getPrice(), Fare.BIKE_RATE_PER_HOUR.getPriceHour());
    }

    @Test
    public void calculateFareUnkownType() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, null, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(NullPointerException.class, () -> fareCalculatorService.calculateFare(ticket, false));
    }

    @Test
    public void calculateFareBikeWithFutureInTime() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() + (60 * 60 * 1000));
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        assertThrows(IllegalArgumentException.class, () -> fareCalculatorService.calculateFare(ticket, false));
    }

    @Test
    public void calculateFareBikeWithLessThanOneHourParkingTime() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.BIKE, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals(((Fare.BIKE_RATE_PER_HOUR.getPriceHour() / 60) * 45), ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithLessThanOneHourParkingTime() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (45 * 60 * 1000));//45 minutes parking time should give 3/4th parking fare
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals((0.75 * Fare.CAR_RATE_PER_HOUR.getPriceHour()), ticket.getPrice());
    }

    @Test
    public void calculateFareCarWithMoreThanADayParkingTime() {
        Date inTime = new Date();
        inTime.setTime(System.currentTimeMillis() - (24 * 60 * 60 * 1000));//24 hours parking time should give 24 * parking fare per hour
        Date outTime = new Date();
        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR, false);
        ticket.setInTime(inTime);
        ticket.setOutTime(outTime);
        ticket.setParkingSpot(parkingSpot);
        fareCalculatorService.calculateFare(ticket, false);
        assertEquals((24 * Fare.CAR_RATE_PER_HOUR.getPriceHour()), ticket.getPrice());
    }

    @Test
    @DisplayName("calculeFare should set ticket price 0 when duration is less than or equal 30 minutes")
    public void calculateFareShouldPrice0DurationLTE30Minutes() {
        // GIVEN
        LocalDateTime inTime30MinutesEarlier = LocalDateTime.now().minus(30, ChronoUnit.MINUTES);
        LocalDateTime inTime29MinutesEarlier = LocalDateTime.now().minus(29, ChronoUnit.MINUTES);
        LocalDateTime outTime = LocalDateTime.now();

        ParkingSpot ps = new ParkingSpot(1, ParkingType.CAR, true);
        Ticket ticket30Minutes = new Ticket();
        ticket30Minutes.setParkingSpot(ps);
        ticket30Minutes.setInTime(Date.from(inTime30MinutesEarlier.atZone(ZoneId.systemDefault()).toInstant()));
        ticket30Minutes.setOutTime(Date.from(outTime.atZone(ZoneId.systemDefault()).toInstant()));

        Ticket ticket29Minutes = new Ticket();
        ticket29Minutes.setParkingSpot(ps);
        ticket29Minutes.setOutTime(Date.from(outTime.atZone(ZoneId.systemDefault()).toInstant()));
        ticket29Minutes.setInTime(Date.from(inTime29MinutesEarlier.atZone(ZoneId.systemDefault()).toInstant()));
        //WHEN
        fareCalculatorService.calculateFare(ticket30Minutes, false);
        fareCalculatorService.calculateFare(ticket29Minutes, false);

        // THEN
        assertEquals(0, ticket30Minutes.getPrice());
        assertEquals(0, ticket29Minutes.getPrice());
    }

    @Test
    @DisplayName("calculareFare should discount 5% when it's recurrent")
    public void calculateFareShouldDiscount5Percent() {
        Mockito.when(ticketDAO.countVisits(ArgumentMatchers.anyString())).thenReturn(2);

        //GIVEN
        LocalDateTime oldTicketStart = LocalDateTime.now().minus(48, ChronoUnit.HOURS);
        LocalDateTime oldTicketEnd = LocalDateTime.now().minus(47, ChronoUnit.HOURS);
        LocalDateTime newTicketStart = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
        LocalDateTime newTicketEnd = LocalDateTime.now();
        ParkingSpot ps = new ParkingSpot(1, ParkingType.CAR, true);

        Ticket oldTicket = new Ticket();
        oldTicket.setVehicleRegNumber("TESTING");
        oldTicket.setParkingSpot(ps);
        oldTicket.setInTime(Date.from(oldTicketStart.atZone(ZoneId.systemDefault()).toInstant()));
        oldTicket.setOutTime(Date.from(oldTicketEnd.atZone(ZoneId.systemDefault()).toInstant()));

        fareCalculatorService = new FareCalculatorService();
        Ticket newTicket = new Ticket();
        newTicket.setVehicleRegNumber("TESTING");
        newTicket.setParkingSpot(ps);
        newTicket.setInTime(Date.from(newTicketStart.atZone(ZoneId.systemDefault()).toInstant()));
        newTicket.setOutTime(Date.from(newTicketEnd.atZone(ZoneId.systemDefault()).toInstant()));
        // WHEN
        fareCalculatorService.calculateFare(newTicket, ticketDAO.countVisits("TESTING") > 1);

        //THEN
        assertEquals(Fare.CAR_RATE_PER_HOUR.getPriceHour() * 0.95, newTicket.getPrice());
    }
}
