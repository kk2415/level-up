package com.levelup.member.domain.repository;


import com.levelup.member.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByMemberId(Long memberId);
}
