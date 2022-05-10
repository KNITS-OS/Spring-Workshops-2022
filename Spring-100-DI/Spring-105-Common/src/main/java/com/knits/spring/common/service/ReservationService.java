package com.knits.spring.common.service;

import com.knits.spring.common.integration.PaypalClient;
import com.knits.spring.common.model.Reservation;
import com.knits.spring.common.model.Seat;
import com.knits.spring.common.model.User;
import com.knits.spring.common.repositories.ReservationRepository;
import com.knits.spring.common.repositories.SeatRepository;
import com.knits.spring.common.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class ReservationService {

    @Getter
    @Setter
    @Autowired
    UserRepository userRepository;

    @Getter
    @Setter
    @Autowired
    SeatRepository seatRepository;

    @Getter
    @Setter
    @Autowired
    ReservationRepository reservationRepository;

    @Getter
    @Setter
    @Autowired
    PaypalClient paypalClient;

    public void reserveSeat(String username, String from, String to, LocalDateTime when){

        log.info("reserveSeat Start Tramsaction");
        User user =userRepository.findUser(username);
        Seat seat = seatRepository.findAvailableSeat(from, to, when);

        paypalClient.sendPayment();

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setSeat(seat);
        reservation.setTime(when);

        reservationRepository.createReservation(reservation);
        log.info("Seat reserved");
        log.info("reserveSeat Commit Tramsaction");
    }
}
