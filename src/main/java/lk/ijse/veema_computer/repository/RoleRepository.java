package lk.ijse.veema_computer.repository;

import lk.ijse.veema_computer.entity.Role;
import lk.ijse.veema_computer.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(RoleName roleName);

    List<Role> findByRoleNameIn(Set<RoleName> roleNames);
}