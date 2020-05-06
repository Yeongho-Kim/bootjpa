package com.web.security;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Log
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/users/list").hasAnyRole("MANAGER","ADMIN")
                .antMatchers("/users/update/**","/boards/**").hasAnyRole("BASIC","MANAGER","ADMIN")
        .and()
                .formLogin().loginPage("/users/login")
        .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
        .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");

        http.userDetailsService(customUserDetailService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomLoginFailureHandler();
    }

}
