package org.example.userservice.utils;

import org.example.userservice.dto.request.RegisterRequestDto;
import org.example.userservice.dto.request.UserProfileRequestDto;

import java.time.LocalDate;

public class TestData {
    public static UserProfileRequestDto getUserProfileRequestDto() {
        return UserProfileRequestDto.builder()
                .firstname("John")
                .lastname("Smith")
                .birthdate(LocalDate.of(1990, 1, 1))
                .build();
    }

    public static RegisterRequestDto getRegisterRequestDto() {
        return RegisterRequestDto.builder()
                .email("test@example.com")
                .passwordEncoded("password")
                .roleId(1)
                .userProfile(getUserProfileRequestDto())
                .build();
    }
}
