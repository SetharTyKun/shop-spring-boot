package edu.itc.shop.notify;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class WelcomeServiceTest {

    @Test
    void sendsWelcomeMessageWithFixedDate() {
        List<String> sent = new ArrayList<>();
        Notifier fake = sent::add;   // fake Notifier that records calls

        Clock fixed = Clock.fixed(Instant.parse("2026-01-15T10:00:00Z"), ZoneOffset.UTC);
        WelcomeService service = new WelcomeService(fake, fixed);

        service.welcome("Sok");

        assertEquals(1, sent.size());
        assertEquals("Welcome Sok! Today is 2026-01-15", sent.get(0));
    }
}