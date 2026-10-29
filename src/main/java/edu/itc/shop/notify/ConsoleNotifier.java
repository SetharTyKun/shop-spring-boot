package edu.itc.shop.notify;   // use the same package as your other notifiers

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Primary
public class ConsoleNotifier implements Notifier {   // your interface name here

    @Override
    public void send(String message) {                // your method signature here
        System.out.println("[CONSOLE] " + message);
    }
}