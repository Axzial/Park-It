package com.parkit.parkingsystem;

import com.parkit.parkingsystem.dao.AbstractDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.service.InteractiveShell;
import lombok.extern.log4j.Log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Log4j
public class App {

    public static void main(String[] args){
        log.info("Initializing Parking System");
        InteractiveShell.loadInterface();
    }
}
