package com.knits.spring.di.demo;

import com.knits.spring.common.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;


@Slf4j
public class TestXmlConfiguration {

	@Test
	public void testReservationProcess (){

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");


		//1) by type
		//ReservationService reservationService =context.getBean(ReservationService.class);

		//2) by id
		//ReservationService reservationService =context.getBean("ReservationServiceName",UserService.class);

		//3) by name
		ReservationService reservationService =context.getBean("ReservationServiceId",ReservationService.class);

		for (String beanName : context.getBeanDefinitionNames()) {
			log.info("[ApplicationContext]: {}",beanName);
		}
		assertThat(reservationService).isNotNull();
		reservationService.reserveSeat("stefano.fiorenza","Tallinn","Naples", LocalDateTime.now());

	}
	


}
