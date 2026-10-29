package edu.itc.shop.notify;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanPrinter implements CommandLineRunner {

    private final ApplicationContext ctx;

    public BeanPrinter(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void run(String... args) {
        System.out.println("Bean count: " + ctx.getBeanDefinitionCount());
        for (String name : ctx.getBeanDefinitionNames()) {
            if (name.contains("Notifier")) {
                System.out.println("Notifier bean: " + name);
            }
        }
    }
}