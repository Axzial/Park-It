package com.parkit.parkingsystem.constants;

import lombok.Getter;

@Getter
public enum DBRequest {

    GET_NEXT_PARKING_SPOT("select min(PARKING_NUMBER) from parking where AVAILABLE = true and TYPE = ?"),
    UPDATE_PARKING_SPOT("update parking set available = ? where PARKING_NUMBER = ?"),
    SAVE_TICKET("insert into ticket(PARKING_NUMBER, VEHICLE_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)"),
    UPDATE_TICKET("update ticket set PRICE=?, OUT_TIME=? where ID=?"),
    GET_TICKET("select t.PARKING_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, p.TYPE from ticket t,parking p where p.parking_number = t.parking_number and t.VEHICLE_REG_NUMBER=? order by t.IN_TIME  limit 1");

    String request;

    DBRequest(String request) {
        this.request = request;
    }
}
