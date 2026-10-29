package edu.itc.shop.notify;

import java.time.Clock;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class WelcomeService {

    private final Notifier notifier;
    private final Clock clock;

    public WelcomeService(Notifier notifier, Clock clock) {
        this.notifier = notifier;
        this.clock = clock;
    }

    public void welcome(String name) {
        notifier.send("Welcome " + name + "! Today is " + LocalDate.now(clock));
    }
}