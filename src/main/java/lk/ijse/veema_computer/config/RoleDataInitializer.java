package lk.ijse.veema_computer.config;

import lk.ijse.veema_computer.entity.Role;
import lk.ijse.veema_computer.enums.RoleName;
import lk.ijse.veema_computer.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        Set<RoleName> allRoleNames = Set.of(RoleName.values());

        Set<RoleName> existingRoleNames = roleRepository.findByRoleNameIn(allRoleNames)
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        List<Role> missingRoles = allRoleNames.stream()
                .filter(roleName -> !existingRoleNames.contains(roleName))
                .map(roleName -> {
                    Role role = new Role();
                    role.setRoleName(roleName);
                    return role;
                })
                .toList();

        if (!missingRoles.isEmpty()) {
            roleRepository.saveAll(missingRoles);
            log.info("Role initialization: {} missing role(s) added.", missingRoles.size());
        } else {
            log.info("Role initialization: 0 missing role(s) added");
        }
    }
}