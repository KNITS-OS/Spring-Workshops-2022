package com.knits.spring.common.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Reservation {

    private Seat seat;
    private User user;
    private LocalDateTime time;
}
