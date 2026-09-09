package lk.ijse.veema_computer.service.impl;

import lk.ijse.veema_computer.dto.request.RegisterRequestDTO;
import lk.ijse.veema_computer.dto.response.UserResponseDTO;
import lk.ijse.veema_computer.entity.Role;
import lk.ijse.veema_computer.entity.User;
import lk.ijse.veema_computer.enums.RoleName;
import lk.ijse.veema_computer.exception.DuplicateResourceException;
import lk.ijse.veema_computer.repository.RoleRepository;
import lk.ijse.veema_computer.repository.UserRepository;
import lk.ijse.veema_computer.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log =
            LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO register(RegisterRequestDTO request) {

        String username = request.getUsername()
                .trim()
                .toLowerCase(Locale.ROOT);

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByUsername(username)
                || userRepository.existsByEmail(email)) {

            throw new DuplicateResourceException(
                    "Username or email is already in use"
            );
        }

        Role defaultRole = roleRepository
                .findByRoleName(RoleName.USER)
                .orElseThrow(() -> new IllegalStateException(
                        "Default USER role has not been initialized"
                ));

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        user.setActive(true);
        user.getRoles().add(defaultRole);

        try {
            user = userRepository.saveAndFlush(user);

        } catch (DataIntegrityViolationException exception) {

            if (isDuplicateKey(exception)) {
                throw new DuplicateResourceException(
                        "Username or email is already in use"
                );
            }

            throw exception;
        }

        Set<String> roleNames = new HashSet<>();

        for (Role role : user.getRoles()) {
            roleNames.add(role.getRoleName().name());
        }

        log.info("Created user record with id={}", user.getId());

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isActive(),
                roleNames,
                user.getCreatedAt()
        );
    }

    private boolean isDuplicateKey(
            DataIntegrityViolationException exception
    ) {
        Throwable cause = exception;

        while (cause != null) {

            if (cause instanceof SQLException sqlException
                    && sqlException.getErrorCode() == 1062) {
                return true;
            }

            cause = cause.getCause();
        }

        return false;
    }
}