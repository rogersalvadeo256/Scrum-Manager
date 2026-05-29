package com.scrummanager.service.contract;

import com.scrummanager.domain.model.User;

public interface CacheInvalidationContract {
    void evictUserCaches(User user);
    void evictProjectCaches();
    void evictTaskCaches(Long projectId);
    void evictFriendshipCaches();
}
