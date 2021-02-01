package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){

        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime().toString());//??
        }

        long in = ticket.getInTime().getTime();
        long out = ticket.getOutTime().getTime();

        //TODO: Some tests are failing here. Need to check if this logic is correct
        long minutes = ((out - in) / 1000) / 60;

        System.out.println("OMG " + minutes);

        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(minutes * (Fare.CAR_RATE_PER_HOUR.getPriceHour() / 60));
                break;
            }
            case BIKE: {
                ticket.setPrice(minutes * (Fare.BIKE_RATE_PER_HOUR.getPriceHour() / 60));
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}