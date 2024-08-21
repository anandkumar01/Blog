package com.project.springboot.springbootproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.springboot.springbootproject.util.constants.Privillages;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

        private static final String[] WHITELIST = {
                        "/",
                        "/login",
                        "/register",
                        "/db-console/**",
                        "/css/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**"
        };

        @Bean
        public static PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .authorizeHttpRequests(requests -> requests
                                        .requestMatchers(WHITELIST).permitAll()
                                        .requestMatchers("/profile/**").authenticated()
                                        .requestMatchers("/admin/**").hasRole("ADMIN")
                                        .requestMatchers("/editor/**").hasAnyRole("ADMIN", "EDITOR")
                                        .requestMatchers("/test")
                                        .hasAuthority(Privillages.ACCESS_ADMIN_PANEL.getPrivillage()))
                        .formLogin(login -> login
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        .usernameParameter("email")
                                        .passwordParameter("password")
                                        .defaultSuccessUrl("/", true)
                                        .failureUrl("/login?error")
                                        .permitAll())
                        .logout(logout -> logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/"))
                        .httpBasic(Customizer.withDefaults());

                // TODO: remove these after upgrading the DB from H2 in file DB

                http.csrf(csrf -> csrf.disable());
                http.headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable());

                return http.build();
        }

}
