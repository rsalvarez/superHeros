package com.superhero.security.config;

import com.superhero.security.handler.AuthenticationFailureHandler;
import com.superhero.security.handler.AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    final private AuthenticationSuccessHandler authenticationSuccessHandler;
    final private AuthenticationFailureHandler authenticationFailureHandler;
    final private LogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          LogoutSuccessHandler logoutSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        users.setUsersByUsernameQuery("select username, password, enabled from users where username = ?");
        users.setAuthoritiesByUsernameQuery("select u.username, r.authority from users u join user_roles ur on ur.user_id = u.id join role r on r.id = ur.role_id where u.username = ? ");
        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authz) -> authz
                        .antMatchers("/heros/**").authenticated()
                )
                .headers().frameOptions().disable();

        // login

        http.exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and().cors()
                .and().csrf().disable()
                .formLogin()
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler);

        // logout
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("remember-me")
                .permitAll();

        // remember-me
        http.rememberMe()
                .useSecureCookie(true)
                .rememberMeCookieName("hs-remember-me")
                .key("bckwdgsk");

        return http.build();
    }
}
