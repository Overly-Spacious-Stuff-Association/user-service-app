package org.example.userservice.service;

import org.example.userservice.dto.request.RegisterRequestDto;
import org.example.userservice.dto.request.UserProfileRequestDto;
import org.example.userservice.entity.Role;
import org.example.userservice.entity.User;
import org.example.userservice.exception.EmailAlreadyExistsException;
import org.example.userservice.exception.RoleNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.repository.RoleRepository;
import org.example.userservice.repository.UserRepository;
import org.example.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.example.userservice.utils.TestData.getRegisterRequestDto;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserProfileService userProfileService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_ShouldSaveUserAndProfile() {
        // Arrange
        RegisterRequestDto request = getRegisterRequestDto();

        Role role = new Role(1, "USER");
        User user = User.builder().email(request.getEmail()).build();

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(userMapper.toEntity(any(), any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);

        // Act
        userService.register(request);

        // Assert
        verify(userRepository).existsByEmail(request.getEmail());
        verify(roleRepository).findById(request.getRoleId());
        verify(userMapper).toEntity(request, role);
        verify(userProfileService).createUserProfile(any(), eq(user));
    }

    @Test
    void register_ShouldThrowEmailExistsException() {
        // Arrange
        RegisterRequestDto request = getRegisterRequestDto();
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(EmailAlreadyExistsException.class,
                () -> userService.register(request));
    }

    @Test
    void register_ShouldThrowRoleNotFoundException() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotFoundException.class,
                () -> userService.register(getRegisterRequestDto()));
    }
}
