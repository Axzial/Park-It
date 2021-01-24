package com.parkit.parkingsystem.model;

import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Data
public class Ticket {

    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;

}
