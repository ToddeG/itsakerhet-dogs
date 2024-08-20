package se.systementor.javasecstart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.systementor.javasecstart.services.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/js/**", "/css/**", "/images/**", "/login/**"
                                , "/logout", "/dogs").permitAll()
                        .anyRequest().authenticated())
                .formLogin((login) -> {
                    login.loginPage("/login");
                    login.failureUrl("/login?error=true");
                    login.successForwardUrl("/");
                    login.permitAll();
                })
                .exceptionHandling((handler) -> {
                    handler.accessDeniedPage("/user/access-denied");
                })
                .logout((logout) -> {
                    logout.permitAll();
                    logout.logoutSuccessUrl("/");
                }).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
