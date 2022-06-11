package com.levelup.api.config;

import com.levelup.api.security.JwtAuthenticationFilter;
import com.levelup.api.security.SecurityLoginFilter;
import com.levelup.api.security.TokenProvider;
import com.levelup.api.service.MemberService;
import com.levelup.core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    /**
     * HttpSecurity : 시큐리티 설정을 위한 오브젝트. 빌더를 제공함
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http.cors() //cors는 따로 설정했으니 기본 설정
                .and()
                .formLogin().disable()
                .csrf().disable() // csrf는 현재 사용하지 않으니 disable
                .httpBasic().disable() // token 방식을 사용하니 basic은 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// session 기반이 아님을 선언
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증 실패시
                .accessDeniedHandler(jwtAccessDeniedHandler); //권한 에러 처리(관리자 권한 등)

        http.authorizeRequests()
                .antMatchers("/api/channel/{\\d+}/manager", "/channel/{\\d+}/member/**",
                        "/channel/{\\d+}/waiting-member/**").access("hasRole('CHANNEL_MANAGER') or hasRole('ADMIN')")
                .antMatchers(HttpMethod.GET, "/api/channel/**").permitAll()
                .antMatchers("/api/channel/**").authenticated()
                .antMatchers( HttpMethod.POST,"/api/member").permitAll()
                .antMatchers("/api/member").authenticated()
                .antMatchers(HttpMethod.GET, "/api/comment/**").permitAll()
                .antMatchers("/api/comment/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/post/**", "/api/{\\d+}/search/count",
                        "/api/{\\d+}/posts/{\\d+}").permitAll()
                .antMatchers("/api/post/**").authenticated()
                .antMatchers(HttpMethod.GET, "/api/notice/**").permitAll()
                .antMatchers("/api/notice/**").authenticated()
                .anyRequest().permitAll();


        http.formLogin().loginPage("/login");

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
        SecurityLoginFilter securityLoginFilter = new SecurityLoginFilter(memberRepository, new TokenProvider());

        securityLoginFilter.setAuthenticationManager(authenticationManager());
        return securityLoginFilter;
    }

}
