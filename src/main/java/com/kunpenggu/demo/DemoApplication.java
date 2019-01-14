package com.kunpenggu.demo;

import com.kunpenggu.demo.rabbitmq.Receiver;
import com.kunpenggu.demo.upload.storage.FileSystemStorageService;
import com.kunpenggu.demo.upload.storage.StorageProperties;
import com.kunpenggu.demo.upload.storage.StorageService;
import lombok.extern.log4j.Log4j2;
import org.h2.server.web.WebServlet;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Log4j2
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication {

    public static final String topicExchangeName = "spring-boot-exchange";

    public static final String queueName = "spring-boot";
/*
    //@Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    //@Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    //@Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    //@Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    //@Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
*/

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

    @Bean
    public CommandLineRunner demo(AccountRepository repos){
        return (args) -> {
            repos.save(new Account("Jack", "Bauer"));
            repos.save(new Account("Kim", "Palmer"));
            repos.save(new Account("Tom", "Dessler"));
            repos.save(new Account("Paul", "Bauer"));

            log.info("Accounts:-----------");
            for(Account acc : repos.findAll()){
                log.info(acc.toString());
            }

            repos.findById(1L)
                    .ifPresent(account -> {
                        log.info("account found:");
                        log.info(account.toString());
                    });

            repos.findByLastName("Bauer").forEach(bauer -> {
                log.info(bauer.toString());
            });
        };
    }


    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }
}


@RestController
class Greeting {
    @GetMapping("greeting")
    String greeting(){
        return "Hello world";
    }
}

@RestController
class ErrorHandler {
    @GetMapping("errors")
    String error(){
        return "ERROR!";
    }
}