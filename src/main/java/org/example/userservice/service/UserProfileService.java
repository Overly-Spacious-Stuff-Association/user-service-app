package org.example.userservice.service;

import org.example.userservice.dto.request.UserProfileRequestDto;
import org.example.userservice.entity.User;

public interface UserProfileService {
    void createUserProfile(UserProfileRequestDto userProfileRequestDto, User user);
}
