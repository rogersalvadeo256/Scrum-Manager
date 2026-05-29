package com.scrummanager.business.contract;

import com.scrummanager.domain.model.User;

import java.util.List;

public interface UserBusinessContract {
    User getById(Long id);
    List<User> searchByName(String name);
    User updateProfilePhoto(Long id, byte[] photo, Long requesterId);
    User findOrThrow(Long id);
}
