package org.example.userservice.service.grpc;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.userservice.exception.EmailAlreadyExistsException;
import org.example.userservice.exception.RoleNotFoundException;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.service.UserService;
import org.lognet.springboot.grpc.GRpcService;
import user.CreateUserRequest;
import user.CreateUserResponse;
import user.UserServiceGrpc;

import static org.example.userservice.utils.MessageHelper.SUCCESS_REGISTER_USER;

@GRpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<CreateUserResponse> responseObserver) {
        try {
            userService.register(userMapper.toDto(request));

            sendSuccessResponse(responseObserver, SUCCESS_REGISTER_USER);
        } catch (EmailAlreadyExistsException | RoleNotFoundException e) {
            sendErrorResponse(responseObserver, e.getMessage());
        } catch (Exception e) {
            handleUnexpectedError(responseObserver, e);
        }
    }

    private void sendSuccessResponse(StreamObserver<CreateUserResponse> observer, String message) {
        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setSuccess(true)
                .setMessage(message)
                .build();
        observer.onNext(response);
        observer.onCompleted();
    }

    private void sendErrorResponse(StreamObserver<CreateUserResponse> observer, String error) {
        CreateUserResponse response = CreateUserResponse.newBuilder()
                .setSuccess(false)
                .setMessage(error)
                .build();
        observer.onNext(response);
        observer.onCompleted();
    }

    private void handleUnexpectedError(StreamObserver<CreateUserResponse> observer, Exception e) {
        observer.onError(Status.INTERNAL
                .withDescription("Internal server error: " + e.getMessage())
                .withCause(e)
                .asRuntimeException());
    }
}
