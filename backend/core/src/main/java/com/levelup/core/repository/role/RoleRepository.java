package com.levelup.core.repository.role;

import com.levelup.core.domain.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByMemberId(Long memberId);
}
