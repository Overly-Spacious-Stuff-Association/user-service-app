package org.example.userservice.service;

import org.example.userservice.dto.request.UserProfileRequestDto;
import org.example.userservice.entity.User;
import org.example.userservice.entity.UserProfile;
import org.example.userservice.mapper.UserProfileMapper;
import org.example.userservice.repository.UserProfileRepository;
import org.example.userservice.service.impl.UserProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.example.userservice.utils.TestData.getUserProfileRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository repository;

    @Mock
    private UserProfileMapper mapper;

    @InjectMocks
    private UserProfileServiceImpl service;

    @Test
    void shouldSaveProfileWithUser() {
        // Arrange
        User user = new User();
        UserProfileRequestDto dto = getUserProfileRequestDto();
        UserProfile profile = new UserProfile();

        when(mapper.toEntity(any(), any())).thenReturn(profile);
        when(repository.save(any())).thenReturn(profile);

        // Act
        service.createUserProfile(dto, user);

        // Assert
        verify(mapper).toEntity(dto, user);
        verify(repository).save(profile);
    }
}
