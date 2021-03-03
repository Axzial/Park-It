package com.parkit.parkingsystem;

import com.parkit.parkingsystem.service.InteractiveShell;
import lombok.extern.log4j.Log4j;

@Log4j
public class App {

    public static void main(String[] args){
        log.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
