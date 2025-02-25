package org.example.userservice.mapper;

import org.example.userservice.dto.request.UserProfileRequestDto;
import org.example.userservice.entity.User;
import org.example.userservice.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import user.CreateUserRequest;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mapping(target = "profileId", ignore = true)
    UserProfile toEntity(UserProfileRequestDto userProfileRequestDto, User user);

    UserProfileRequestDto toDto(CreateUserRequest createUserRequest);
}
