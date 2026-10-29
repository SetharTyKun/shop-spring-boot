package edu.itc.shop.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailNotifier implements Notifier {
    private static final Logger log = LoggerFactory.getLogger(EmailNotifier.class);

    @PostConstruct void ready()   { log.info("EmailNotifier ready"); }
    @PreDestroy   void closing()  { log.info("EmailNotifier closing"); }

    @Override
    public void send(String to, String text) {
        log.info("EMAIL to={} text={}", to, text);
    }
}