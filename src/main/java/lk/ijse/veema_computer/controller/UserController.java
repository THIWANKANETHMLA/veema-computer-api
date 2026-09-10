package lk.ijse.veema_computer.controller;

import lk.ijse.veema_computer.constant.CommonResponse;
import lk.ijse.veema_computer.constant.ResponseCode;
import lk.ijse.veema_computer.constant.ResponseMessage;
import lk.ijse.veema_computer.dto.response.UserResponseDTO;
import lk.ijse.veema_computer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CommonResponse<UserResponseDTO>> getCurrentUser(
            Authentication authentication
    ) {
        UserResponseDTO user =
                userService.getCurrentUser(authentication.getName());

        CommonResponse<UserResponseDTO> response =
                new CommonResponse<>(
                        ResponseCode.SUCCESS,
                        user,
                        ResponseMessage.OPERATION_SUCCESS
                );

        return ResponseEntity.ok(response);
    }
}
