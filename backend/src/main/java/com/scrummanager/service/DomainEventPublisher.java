package com.scrummanager.service;

import com.scrummanager.service.contract.DomainEventPublisherContract;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomainEventPublisher implements DomainEventPublisherContract {

    private final ObjectProvider<RabbitTemplate> rabbitTemplateProvider;

    @Value("${app.messaging.exchange}")
    private String exchange;

    public void publish(String routingKey,
                        Long actorId,
                        String resourceType,
                        Long resourceId,
                        Map<String, Object> payload) {
        DomainEvent event = new DomainEvent(
                UUID.randomUUID().toString(),
                routingKey,
                OffsetDateTime.now(ZoneOffset.UTC),
                actorId,
                resourceType,
                resourceId,
                payload == null ? Map.of() : payload);

        RabbitTemplate rabbitTemplate = rabbitTemplateProvider.getIfAvailable();
        if (rabbitTemplate == null) {
            log.info("RabbitTemplate unavailable, skipping event {} for {}:{}", routingKey, resourceType, resourceId);
            return;
        }

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, event);
        } catch (AmqpException ex) {
            log.warn("Could not publish event {}: {}", routingKey, ex.getMessage());
        }
    }

    public record DomainEvent(String eventId,
                              String eventType,
                              OffsetDateTime occurredAt,
                              Long actorId,
                              String resourceType,
                              Long resourceId,
                              Map<String, Object> payload) {
    }
}
