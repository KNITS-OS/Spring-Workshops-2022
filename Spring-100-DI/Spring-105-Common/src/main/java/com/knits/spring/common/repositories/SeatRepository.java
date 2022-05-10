package com.knits.spring.common.repositories;

import com.knits.spring.common.model.Seat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SeatRepository {

    @Getter
    @Setter
    @Autowired
    private DatabaseConnectionPool databaseConnectionPool;

    public Seat findAvailableSeat(String departure, String destination,LocalDateTime time){
        databaseConnectionPool.connections();

        log.info("findAvailableSeat");
        return new Seat();
    }


}
