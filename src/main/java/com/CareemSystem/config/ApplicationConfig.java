package com.CareemSystem.config;


import com.CareemSystem.auditing.ApplicationAuditingAware;
import com.CareemSystem.user.Model.Client;
import com.CareemSystem.user.Repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ClientRepository clientRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return phone -> clientRepository.findByPhone(phone)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuditorAware<Integer> auditorAware(){
        return new ApplicationAuditingAware();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider =  new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public FilterRegistrationBean<CorsFilter> corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowedOrigins(List.of("*")); // Allow requests from any origin (not recommended for production)
    //     config.setAllowCredentials(true);
    //     config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
    //     config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    //     source.registerCorsConfiguration("/**", config);
    //     FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter());
    //     bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    //     return bean;
    // }
}

