package com.levelup.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.levelup.api.filter.*;
import com.levelup.member.util.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true) //secured 애노테이션 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final LoginFailureHandler loginFailureHandler;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors() //cors는 따로 설정했으니 기본 설정
                .and()
                .formLogin().disable()
                .csrf().disable()
                .httpBasic().disable() // token 방식을 사용하니 basic은 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// session 기반이 아님을 선언
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증 실패시
                    .accessDeniedHandler(jwtAccessDeniedHandler); //권한 에러 처리(관리자 권한 등)

        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                /*====================================================================================================*/
                .antMatchers(POST,"/api/*/sign-up", "/api/*/members/image", "/api/*/login", "/api/*/sign-in/failure").permitAll()
                .antMatchers("/api/*/members/**").authenticated()
                /*====================================================================================================*/
                .antMatchers(GET, "/api/*/channels/**").permitAll()
                .antMatchers("/api/*/channels/{\\d+}/manager").hasAnyRole("CHANNEL_MANAGER", "ADMIN")
                .antMatchers("/api/*/channels/**").hasAnyRole("MEMBER", "CHANNEL_MANAGER", "ADMIN")

                .antMatchers(GET, "/api/*/channel-articles/**").permitAll()
                .antMatchers("/api/*/channel-articles/**").hasAnyRole("MEMBER", "CHANNEL_MANAGER", "ADMIN")

                .antMatchers(GET, "/api/*/channel/comments/**").permitAll()
                .antMatchers("/api/*/channel/comments/**").hasAnyRole("MEMBER", "CHANNEL_MANAGER", "ADMIN")

                .antMatchers(GET, "/api/*/channel/members/**").permitAll()
                .antMatchers("/api/*/channel/members/**").hasAnyRole("MEMBER", "CHANNEL_MANAGER", "ADMIN")

                .antMatchers("/api/*/channel/votes/**").authenticated()
                /*====================================================================================================*/
                .antMatchers(GET, "/api/*/articles/**").permitAll()
                .antMatchers("/api/*/articles/**").hasAnyRole("MEMBER", "CHANNEL_MANAGER", "ADMIN")

                .antMatchers(GET, "/api/*/comments/**").permitAll()
                .antMatchers("/api/*/comments/**").hasAnyRole("MEMBER", "CHANNEL_MANAGER", "ADMIN")

                .antMatchers("/api/*/votes/**").authenticated();
        http
                .addFilterAfter(getSecurityLoginFilter(), CorsFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, getSecurityLoginFilter().getClass());
    }

    private LoginFilter getSecurityLoginFilter() throws Exception {
        LoginFilter securityLoginFilter = new LoginFilter(objectMapper, new TokenProvider());

        securityLoginFilter.setAuthenticationManager(authenticationManager());
        securityLoginFilter.setFilterProcessesUrl("/api/*/login");
        securityLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return securityLoginFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("^(?!/api/).*");
    }
}
