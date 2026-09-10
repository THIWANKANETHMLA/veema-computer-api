package lk.ijse.veema_computer.service;

import lk.ijse.veema_computer.dto.request.LoginRequestDTO;
import lk.ijse.veema_computer.dto.request.RegisterRequestDTO;
import lk.ijse.veema_computer.dto.response.LoginResponseDTO;
import lk.ijse.veema_computer.dto.response.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(RegisterRequestDTO request);
    LoginResponseDTO login(LoginRequestDTO request);
}
