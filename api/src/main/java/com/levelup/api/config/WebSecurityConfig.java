package com.levelup.api.config;

import com.levelup.api.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

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
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()

                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                    .antMatchers("/api/member/{email}/image",
                            "/api/notice", "/api/notice/{noticeId}",
                            "/api/channel-notice", "/api/channel-notice/{id}",
                            "/api/comment", "/api/comment/reply",
                            "/api/post", "/api/post/{postId}",
                            "/api/channel/{channelId}/manager",
                            "/api/channel", "/api/channel/{channelId}",
                            "/api/channel/{channelId}/waiting-member",
                            "/api/channel/{channelId}/member/{email}",
                            "/post/create", "/channel/study/create", "/channel/project/create").authenticated()
                    .anyRequest().permitAll()
                .and()
                .formLogin()
                        .loginPage("/member/login?redirectURL=")
                        .permitAll()
                        .and()
                .logout()
                        .permitAll();


        http.addFilterAfter(
                jwtAuthenticationFilter,
                CorsFilter.class
        );
    }
}
