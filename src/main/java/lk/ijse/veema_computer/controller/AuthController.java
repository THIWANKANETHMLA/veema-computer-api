package lk.ijse.veema_computer.controller;

import jakarta.validation.Valid;
import lk.ijse.veema_computer.constant.CommonResponse;
import lk.ijse.veema_computer.constant.ResponseCode;
import lk.ijse.veema_computer.constant.ResponseMessage;
import lk.ijse.veema_computer.dto.request.LoginRequestDTO;
import lk.ijse.veema_computer.dto.request.RegisterRequestDTO;
import lk.ijse.veema_computer.dto.response.LoginResponseDTO;
import lk.ijse.veema_computer.dto.response.UserResponseDTO;
import lk.ijse.veema_computer.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<UserResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        UserResponseDTO user = authService.register(request);

        CommonResponse<UserResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.CREATED,
                        user,
                        ResponseMessage.RESOURCE_CREATED
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<LoginResponseDTO>> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        LoginResponseDTO loginResponse =
                authService.login(request);

        CommonResponse<LoginResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        loginResponse,
                        ResponseMessage.LOGIN_SUCCESS
                );

        return ResponseEntity.ok(response);
    }
}