package com.example.config.security;

import com.example.config.CorsFilter;
import com.example.controller.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAuthenticationService userAuthenticationService;


    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.inMemoryAuthentication();

                    auth.userDetailsService(userAuthenticationService)
                    .passwordEncoder(passwordEncoder());


    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(corsFilter(), SessionManagementFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

        http.sessionManagement().maximumSessions(10).sessionRegistry(sessionRegistry()) //TODO per user ?
                .expiredUrl("/sessionExpired.html");

        http .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }


}
