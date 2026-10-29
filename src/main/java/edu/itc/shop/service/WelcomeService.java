package edu.itc.shop.service;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WelcomeService {
    private final Notifier notifier;
    private final Clock clock;

    public WelcomeService(Notifier notifier, Clock clock) {
        this.notifier = notifier;
        this.clock = clock;
    }

    public void welcome(String name) {
        String time = LocalTime.now(clock).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        notifier.send(name, "Welcome " + name + "! It is " + time);
    }
}