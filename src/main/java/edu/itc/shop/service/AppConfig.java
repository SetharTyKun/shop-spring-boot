package edu.itc.shop.service;

import java.time.Clock;
import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    @Bean Clock clock() { return Clock.systemDefaultZone(); }

    @Bean WelcomeService emailWelcome(EmailNotifier n, Clock c) { return new WelcomeService(n, c); }
    @Bean WelcomeService smsWelcome(SmsNotifier n, Clock c)     { return new WelcomeService(n, c); }

    @Bean @Scope("prototype")
    Object ticket() { return new Object(); }   // a new instance on every request
}