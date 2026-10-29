package edu.itc.shop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class WelcomeServiceTest {
    @Test
    void sendsWelcomeMessage() {
        List<String> calls = new ArrayList<>();
        Notifier fake = (to, text) -> calls.add(to + "|" + text);
        Clock fixed = Clock.fixed(Instant.parse("2026-01-15T09:15:02Z"), ZoneOffset.UTC);

        new WelcomeService(fake, fixed).welcome("ann");

        assertEquals(List.of("ann|Welcome ann! It is 09:15:02"), calls);
    }
}