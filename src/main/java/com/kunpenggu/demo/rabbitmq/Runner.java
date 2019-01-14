package com.kunpenggu.demo.rabbitmq;

import com.kunpenggu.demo.DemoApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.concurrent.TimeUnit;

//@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabittTemplate;
    private final Receiver receiver;

    public Runner(RabbitTemplate rabittTemplate, Receiver receiver) {
        this.rabittTemplate = rabittTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending Message----");
        rabittTemplate.convertAndSend(DemoApplication.topicExchangeName);
        receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }
}
