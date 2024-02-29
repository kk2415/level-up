package com.levelup.api.controller.v1.dto;

import com.levelup.common.domain.domain.Skill;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SkillDto {

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Response {
        private Long id;
        private String name;

        public static Response from(Skill skill) {
            return new Response(skill.getId(), skill.getName());
        }
    }
}
