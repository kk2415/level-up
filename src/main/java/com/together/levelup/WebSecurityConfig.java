package com.together.levelup;

import com.together.levelup.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    /**
     * HttpSecurity : 시큐리티 설정을 위한 오브젝트. 빌더를 제공함
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // http 시큐리티 빌더
        http.cors() //cors는 따로 설정했으니 기본 설정
                .and()
                .csrf() // csrf는 현재 사용하지 않으니 disable
                    .disable()
                .httpBasic() // token 방식을 사용하니 basic은 disable
                    .disable()
                .sessionManagement() // session 기반이 아님을 선언
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // "/", "/auth/**", "/api/member/login" 이 경로는 제외
                    .antMatchers("/**")
                    .permitAll()
                .anyRequest()
                    .authenticated();

        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
    }
}
