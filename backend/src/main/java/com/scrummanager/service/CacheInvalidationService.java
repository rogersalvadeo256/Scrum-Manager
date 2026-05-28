package com.scrummanager.service;

import com.scrummanager.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CacheInvalidationService {

    private final CacheManager cacheManager;

    public void evictUserCaches(User user) {
        evict("user-by-id", user.getId());
        clear("user-search");
        evict("auth-user-details", user.getUsername().toLowerCase(Locale.ROOT));
        evict("auth-user-details", user.getEmail().toLowerCase(Locale.ROOT));
    }

    public void evictProjectCaches() {
        clear("project-mine", "project-member", "project-invites");
    }

    public void evictTaskCaches(Long projectId) {
        evict("project-tasks", projectId);
        evict("project-sprints", projectId);
    }

    public void evictFriendshipCaches() {
        clear("friendships-pending", "friendships-list");
    }

    private void evict(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evictIfPresent(key);
        }
    }

    private void clear(String... cacheNames) {
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        }
    }
}
