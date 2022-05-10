package com.knits.spring.common.repositories;

import com.knits.spring.common.model.Reservation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReservationRepository {

    @Getter
    @Setter
    @Autowired
    private DatabaseConnectionPool databaseConnectionPool;

    public void createReservation(Reservation reservation){
        databaseConnectionPool.connections();
        log.info("save Reservation");
    }
}
