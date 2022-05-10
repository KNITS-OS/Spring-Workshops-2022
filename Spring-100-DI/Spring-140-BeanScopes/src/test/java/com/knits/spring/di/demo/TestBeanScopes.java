package com.knits.spring.di.demo;

import com.knits.spring.common.repositories.DatabaseConnectionPool;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class TestBeanScopes {

	@Test
	public void testBeanScopes (){
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		DatabaseConnectionPool connPool1= context.getBean(DatabaseConnectionPool.class);
		DatabaseConnectionPool connPool2= context.getBean(DatabaseConnectionPool.class);
		log.info("Same object? {}", connPool1.equals(connPool2));
		assertThat(connPool1).isEqualTo(connPool2);

		//prototype scope
		//assertThat(connPool1).isNotEqualTo(connPool2);
	}


	
}
