package com.knits.spring.di.demo;

import com.knits.spring.common.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j

public class TestJavaConfiguration {

	@Test
	public void testReservationProcess (){
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		for (String beanName : context.getBeanDefinitionNames()) {
			log.info("[ApplicationContext]: {}",beanName);
		}
		ReservationService reservationService =context.getBean(ReservationService.class);
		assertThat(reservationService).isNotNull();
		reservationService.reserveSeat("stefano.fiorenza","Tallinn","Naples", LocalDateTime.now());
	}

}
