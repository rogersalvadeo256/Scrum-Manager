package com.scrummanager.service.contract;

import java.util.Map;

public interface DomainEventPublisherContract {
    void publish(String routingKey, Long actorId, String resourceType, Long resourceId, Map<String, Object> payload);
}
