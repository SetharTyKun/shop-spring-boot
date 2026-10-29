package edu.itc.shop.notify;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailNotifier implements Notifier {

    @Override
    public void send(String message) {
        System.out.println("[EMAIL] " + message);
    }
}