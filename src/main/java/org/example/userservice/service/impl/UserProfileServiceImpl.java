package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.UserProfileRequestDto;
import org.example.userservice.entity.User;
import org.example.userservice.mapper.UserProfileMapper;
import org.example.userservice.repository.UserProfileRepository;
import org.example.userservice.service.UserProfileService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public void createUserProfile(UserProfileRequestDto userProfileRequestDto, User user) {
        userProfileRepository.save(userProfileMapper.toEntity(userProfileRequestDto, user));
    }
}
