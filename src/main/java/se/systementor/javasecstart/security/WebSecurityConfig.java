package se.systementor.javasecstart.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import se.systementor.javasecstart.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2-> {
                    oauth2.userInfoEndpoint(ep -> {
                        ep.userAuthoritiesMapper(this.userAuthoritiesMapper());
                    });
                })
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

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {

        return(authorities) -> {
            List<SimpleGrantedAuthority> mappedAuthorities = new ArrayList<>();

            authorities.forEach(authority -> {
                if(authority instanceof OAuth2UserAuthority oAuth2UserAuthority){
                    Map<String, Object> userAttributes = oAuth2UserAuthority.getAttributes();

                    String login = userAttributes.get("login").toString();

                    if(login.equals("ToddeG")){
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                    }
                }
            });

            return mappedAuthorities;
        };

    }

}
