package com.knits.spring.common.repositories;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseConnectionPool {

    @Getter
    @Setter
    private String dbUsername;

    @Getter
    @Setter
    private String dbPassword;
    public void connections(){
        log.info("manage connection");
    }
}
