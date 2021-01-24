package com.parkit.parkingsystem.constants;

import lombok.Getter;

@Getter
public enum Fare {
    BIKE_RATE_PER_HOUR(1.0),
    CAR_RATE_PER_HOUR(1.5);

    double priceHour;

    Fare(double priceHour) {
        this.priceHour = priceHour;
    }
}
