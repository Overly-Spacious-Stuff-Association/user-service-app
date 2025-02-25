package org.example.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.request.RegisterRequestDto;
import org.example.userservice.entity.Role;
import org.example.userservice.entity.User;
import org.example.userservice.exception.EmailAlreadyExistsException;
import org.example.userservice.exception.RoleNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.repository.RoleRepository;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.UserProfileService;
import org.example.userservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserProfileService userProfileService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public void register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException(registerRequestDto.getEmail());
        }

        Role role = roleRepository.findById(registerRequestDto.getRoleId())
                .orElseThrow(RoleNotFoundException::new);

        User user = userRepository.save(userMapper.toEntity(registerRequestDto, role));

        userProfileService.createUserProfile(registerRequestDto.getUserProfile(), user);
    }
}
