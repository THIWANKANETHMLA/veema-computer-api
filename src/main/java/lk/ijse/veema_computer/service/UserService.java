package lk.ijse.veema_computer.service;

import lk.ijse.veema_computer.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO getCurrentUser(String username);
}
