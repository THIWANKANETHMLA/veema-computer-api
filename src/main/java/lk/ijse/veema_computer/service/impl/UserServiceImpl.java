package lk.ijse.veema_computer.service.impl;

import lk.ijse.veema_computer.dto.response.UserResponseDTO;
import lk.ijse.veema_computer.entity.User;
import lk.ijse.veema_computer.exception.ResourceNotFoundException;
import lk.ijse.veema_computer.repository.UserRepository;
import lk.ijse.veema_computer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponseDTO getCurrentUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found: " + username
                        )
                );

        Set<String> roles = user.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toSet());

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isActive(),
                roles,
                user.getCreatedAt()
        );
    }
}
