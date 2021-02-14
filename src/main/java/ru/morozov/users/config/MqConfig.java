package ru.morozov.users.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Value("${active-mq.UserRegistered-exchange}")
    private String userRegisteredExchange;

    @Bean
    TopicExchange userRegisteredExchange() {
        return new TopicExchange(userRegisteredExchange);
    }
}
