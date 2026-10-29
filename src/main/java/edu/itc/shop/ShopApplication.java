package edu.itc.shop;

import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import edu.itc.shop.service.WelcomeService;

@SpringBootApplication
public class ShopApplication {
    private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);

    public static void main(String[] args) { SpringApplication.run(ShopApplication.class, args); }

    @Bean
    CommandLineRunner runner(ApplicationContext ctx, WelcomeService emailWelcome, WelcomeService smsWelcome) {
        return args -> {
            var notifiers = Arrays.stream(ctx.getBeanDefinitionNames())
                    .filter(n -> n.contains("Notifier")).toList();
            log.info("{} beans; notifiers = {}", ctx.getBeanDefinitionCount(), notifiers);

            emailWelcome.welcome("ann");
            smsWelcome.welcome("bob");

            log.info("prototype instances differ: {}", ctx.getBean("ticket") != ctx.getBean("ticket"));
        };
    }
}