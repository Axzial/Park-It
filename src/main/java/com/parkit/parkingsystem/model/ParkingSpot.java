package com.parkit.parkingsystem.model;

import com.parkit.parkingsystem.constants.ParkingType;
import lombok.Data;

@Data
public class ParkingSpot {
    private int id;
    private ParkingType parkingType;
    private boolean isAvailable;

    public ParkingSpot(int id, ParkingType parkingType, boolean isAvailable) {
        this.id = id;
        this.parkingType = parkingType;
        this.isAvailable = isAvailable;
    }


}
