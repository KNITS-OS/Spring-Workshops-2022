package com.knits.spring.common.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaypalClient {

    public void sendPayment (){
        log.info("Sent payment");
    }
}
