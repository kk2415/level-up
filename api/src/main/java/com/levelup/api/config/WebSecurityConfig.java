package com.levelup.api.config;

import com.levelup.api.filter.JwtAuthenticationFilter;
import com.levelup.api.filter.SecurityLoginFilter;
import com.levelup.api.security.TokenProvider;
import com.levelup.api.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HttpSecurity : 시큐리티 설정을 위한 오브젝트. 빌더를 제공함
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http.cors() //cors는 따로 설정했으니 기본 설정
                .and()
                .csrf().disable() // csrf는 현재 사용하지 않으니 disable
                .httpBasic().disable() // token 방식을 사용하니 basic은 disable
                .sessionManagement() // session 기반이 아님을 선언
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증 실패시
                .accessDeniedHandler(jwtAccessDeniedHandler) //권한 에러 처리(관리자 권한 등)
                .and()
                .authorizeRequests()
                .antMatchers("/**", "/api/**").permitAll();

        http.addFilterAfter(getSecurityLoginFilter(), CorsFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, getSecurityLoginFilter().getClass());

    }

    //select pwd from users where email=?
    //데이터베이스 pwd와 사용자가 입력한 pwd 비교
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
    }

    private SecurityLoginFilter getSecurityLoginFilter() throws Exception {
        SecurityLoginFilter securityLoginFilter = new SecurityLoginFilter(memberService, new TokenProvider());

        securityLoginFilter.setAuthenticationManager(authenticationManager());
        return securityLoginFilter;
    }

}
