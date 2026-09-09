package lk.ijse.veema_computer.config;

import lk.ijse.veema_computer.entity.Role;
import lk.ijse.veema_computer.enums.RoleName;
import lk.ijse.veema_computer.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RoleDataInitializer implements CommandLineRunner {

    private static final Logger log =
            LoggerFactory.getLogger(RoleDataInitializer.class);

    private final RoleRepository roleRepository;

    public RoleDataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {

        int addedCount = 0;

        for (RoleName roleName : RoleName.values()) {

            if (!roleRepository.existsByRoleName(roleName)) {

                Role role = new Role();
                role.setRoleName(roleName);

                roleRepository.save(role);
                addedCount++;
            }
        }

        log.info(
                "Role initialization: {} missing role(s) added",
                addedCount
        );
    }
}