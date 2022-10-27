package com.levelup.api.config;

import com.levelup.member.domain.entity.MemberPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EntityAuditorProvider implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof MemberPrincipal)) {
            return Optional.of("unknown");
        }
        System.out.println(authentication.getPrincipal());
        MemberPrincipal member = (MemberPrincipal) authentication.getPrincipal();
        return Optional.ofNullable(member.getUsername());
    }
}
