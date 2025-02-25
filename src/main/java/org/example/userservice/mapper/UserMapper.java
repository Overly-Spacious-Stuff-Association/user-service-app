package org.example.userservice.mapper;

import org.example.userservice.dto.request.RegisterRequestDto;
import org.example.userservice.entity.Role;
import org.example.userservice.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import user.CreateUserRequest;

@Mapper(componentModel = "spring", uses = {UserProfileMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mapping(target = "userProfile", source = "createUserRequest")
    RegisterRequestDto toDto(CreateUserRequest createUserRequest);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User toEntity(RegisterRequestDto registerRequestDto, Role role);
}
