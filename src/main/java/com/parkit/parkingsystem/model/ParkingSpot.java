package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;
import lombok.Data;

@Data
public class ParkingSpot {

    private int number;
    private ParkingType parkingType;
    private boolean isAvailable;

    public ParkingSpot(int number, ParkingType parkingType, boolean isAvailable) {
        this.number = number;
        this.parkingType = parkingType;
        this.isAvailable = isAvailable;
    }

}
