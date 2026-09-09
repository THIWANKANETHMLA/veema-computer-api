package lk.ijse.veema_computer.service;

import lk.ijse.veema_computer.dto.request.RegisterRequestDTO;
import lk.ijse.veema_computer.dto.response.UserResponseDTO;

public interface AuthService {

    UserResponseDTO register(RegisterRequestDTO request);
}
