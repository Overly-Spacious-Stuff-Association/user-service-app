package org.example.userservice.service;

import org.example.userservice.dto.request.RegisterRequestDto;

public interface UserService {
    void register(RegisterRequestDto registerRequestDto);
}
