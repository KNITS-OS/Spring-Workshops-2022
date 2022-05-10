package com.knits.spring.di.demo;

import com.knits.spring.common.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
@Slf4j
public class TestClasspathScanning {

	@Test
	public void testReservationProcess (){

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		for (String beanName : context.getBeanDefinitionNames()) {
			log.info("[ApplicationContext]: {}",beanName);
		}
		ReservationService reservationService =context.getBean(ReservationService.class);
		assertThat(reservationService).isNotNull();
		reservationService.reserveSeat("stefano.fiorenza","Tallinn","Naples", LocalDateTime.now());

	}


}
