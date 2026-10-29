package edu.itc.shop.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsNotifier implements Notifier {
    private static final Logger log = LoggerFactory.getLogger(SmsNotifier.class);

    @PostConstruct
    void ready() {
        log.info("SmsNotifier ready");
    }

    @Override
    public void send(String to, String text) {
        log.info("SMS to={} text={}", to, text);
    }
}