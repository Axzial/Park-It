package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public final TicketDAO ticketDAO = new TicketDAO();

    public static final double RECURRENT_DISCOUNT = 0.05;
    public static final int FREE_MIN_DURATION = 30;




    /**
     * /!\ This method mutates price field of ticket
     *
     * @param ticket
     */
    public void calculateFare(Ticket ticket, Boolean isDiscounted) {

        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:");
        }

        long in = ticket.getInTime().getTime();
        long out = ticket.getOutTime().getTime();

        long minutes = ((out - in) / 1000) / 60;

        //FREE 30 MINUTES
        if (minutes <= FREE_MIN_DURATION) {
            ticket.setPrice(0);
            return;
        }

        //DISCOUNT ?
        double discount = isDiscounted ? RECURRENT_DISCOUNT : 0;

        //FINALLY
        switch (ticket.getParkingSpot().getParkingType()) {
            case CAR: {
                ticket.setPrice(minutes * (Fare.CAR_RATE_PER_HOUR.getPriceHour() / 60) * (1 - discount));
                break;
            }
            case BIKE: {
                ticket.setPrice(minutes * (Fare.BIKE_RATE_PER_HOUR.getPriceHour() / 60) * (1 - discount));
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}