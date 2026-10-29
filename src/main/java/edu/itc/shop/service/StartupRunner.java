package edu.itc.shop.service;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

    private final ApplicationContext ctx;
    private final WelcomeService emailWelcome;
    private final WelcomeService smsWelcome;

    public StartupRunner(ApplicationContext ctx,
                         @Qualifier("emailWelcome") WelcomeService emailWelcome,
                         @Qualifier("smsWelcome") WelcomeService smsWelcome) {
        this.ctx = ctx;
        this.emailWelcome = emailWelcome;
        this.smsWelcome = smsWelcome;
    }

    @Override
    public void run(String... args) {
        var notifiers = Arrays.stream(ctx.getBeanDefinitionNames())
                .filter(n -> n.contains("Notifier")).toList();
        log.info("{} beans; notifiers = {}", ctx.getBeanDefinitionCount(), notifiers);

        emailWelcome.welcome("ann");
        smsWelcome.welcome("bob");

        log.info("prototype instances differ: {}", ctx.getBean("ticket") != ctx.getBean("ticket"));
    }
}