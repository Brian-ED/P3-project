package com.example.application.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.application.security.ui.view.LoginView;
import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;

@EnableWebSecurity
@Configuration
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.with(
            VaadinSecurityConfigurer.vaadin(),
            configurer -> configurer.loginView(LoginView.class)
        );

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        UserDetails citizen      = User.withUsername("citizen").password("{noop}citizen").roles("CITIZEN").build();
        UserDetails sleepAdvisor = User.withUsername("advisor").password("{noop}advisor").roles("ADVISOR").build();
        UserDetails admin        = User.withUsername("admin"  ).password("{noop}admin"  ).roles("ADMIN"  ).build();
        return new InMemoryUserDetailsManager(citizen, sleepAdvisor, admin);
    }
}
