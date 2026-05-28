package com.scrummanager.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    @Bean
    TopicExchange domainEventsExchange(@Value("${app.messaging.exchange}") String exchangeName) {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Queue authEventsQueue(@Value("${app.messaging.auth-queue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Queue projectEventsQueue(@Value("${app.messaging.project-queue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Queue securityEventsQueue(@Value("${app.messaging.security-queue}") String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding authEventsBinding(Queue authEventsQueue, TopicExchange domainEventsExchange) {
        return BindingBuilder.bind(authEventsQueue).to(domainEventsExchange).with("auth.#");
    }

    @Bean
    Binding projectEventsBinding(Queue projectEventsQueue, TopicExchange domainEventsExchange) {
        return BindingBuilder.bind(projectEventsQueue).to(domainEventsExchange).with("project.#");
    }

    @Bean
    Binding securityEventsBinding(Queue securityEventsQueue, TopicExchange domainEventsExchange) {
        return BindingBuilder.bind(securityEventsQueue).to(domainEventsExchange).with("security.#");
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                  Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }
}
