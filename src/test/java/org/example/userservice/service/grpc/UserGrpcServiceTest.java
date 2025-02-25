package org.example.userservice.service.grpc;

import io.grpc.stub.StreamObserver;
import org.example.userservice.exception.EmailAlreadyExistsException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import user.CreateUserRequest;
import user.CreateUserResponse;

import static org.example.userservice.utils.TestData.getRegisterRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserGrpcServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private StreamObserver<CreateUserResponse> responseObserver;

    @InjectMocks
    private UserGrpcService userGrpcService;

    @Test
    void createUser_ShouldSendSuccessResponse() {
        // Arrange
        CreateUserRequest request = CreateUserRequest.newBuilder()
                .setEmail("test@example.com")
                .build();

        when(userMapper.toDto(any())).thenReturn(getRegisterRequestDto());

        // Act
        userGrpcService.createUser(request, responseObserver);

        // Assert
        verify(responseObserver).onNext(any());
        verify(responseObserver).onCompleted();
    }

    @Test
    void createUser_ShouldHandleEmailExistsException() {
        // Arrange
        doThrow(new EmailAlreadyExistsException("test@example.com")).when(userService).register(any());

        // Act
        userGrpcService.createUser(CreateUserRequest.newBuilder().build(), responseObserver);

        // Assert
        verify(responseObserver).onNext(argThat(response ->
                !response.getSuccess() &&
                        response.getMessage().contains("already exists")
        ));
    }

    @Test
    void createUser_ShouldHandleInternalError() {
        // Arrange
        doThrow(new RuntimeException("DB error")).when(userService).register(any());

        // Act
        userGrpcService.createUser(CreateUserRequest.newBuilder().build(), responseObserver);

        // Assert
        verify(responseObserver).onError(any());
    }
}