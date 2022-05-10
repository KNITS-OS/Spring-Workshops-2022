package com.knits.spring.di.demo;

import com.knits.spring.common.integration.PaypalClient;
import com.knits.spring.common.repositories.DatabaseConnectionPool;
import com.knits.spring.common.repositories.ReservationRepository;
import com.knits.spring.common.repositories.SeatRepository;
import com.knits.spring.common.repositories.UserRepository;
import com.knits.spring.common.service.ReservationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ "com.knits.spring.common.integration" })
public class AppConfig {


	@Bean
	public DatabaseConnectionPool connectionPool(){
		return new DatabaseConnectionPool();
	}

	@Bean
	public UserRepository userRepository(DatabaseConnectionPool connectionPool){
		UserRepository userRepository = new UserRepository();
		userRepository.setDatabaseConnectionPool(connectionPool);
		return userRepository;
	}

	@Bean
	public SeatRepository seatRepository(){
		SeatRepository seatRepository = new SeatRepository();
		seatRepository.setDatabaseConnectionPool(connectionPool());
		return seatRepository;
	}

	@Bean
	public ReservationRepository reservationRepository(){
		ReservationRepository reservationRepository = new ReservationRepository();
		reservationRepository.setDatabaseConnectionPool(connectionPool());
		return reservationRepository;
	}

	@Bean
	public ReservationService reservationService(DatabaseConnectionPool connectionPool,PaypalClient paypalClient){
		ReservationService reservationService = new ReservationService();
		reservationService.setReservationRepository(reservationRepository());
		reservationService.setPaypalClient(paypalClient);
		reservationService.setSeatRepository(seatRepository());
		reservationService.setUserRepository(userRepository(connectionPool));
		return reservationService;
		
	}


}
