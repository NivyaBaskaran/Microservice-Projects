package com.interprep.quiz_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {



    //Configuring our own security features by disabling default spring settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        //Builder Pattern
        http
                .csrf(customizer ->customizer.disable())

                .authorizeHttpRequests(request ->request.anyRequest().authenticated())

                .httpBasic(Customizer.withDefaults())

                .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

//        //Disable  csrf token
//        http.csrf(customizer ->customizer.disable());
//
//        //Enabling security for the requests
//        http.authorizeHttpRequests(request ->request.anyRequest().authenticated());
//
//        //Provide Form Login
//        http.formLogin(Customizer.withDefaults());
//        http.httpBasic(Customizer.withDefaults());
//
//        //Making the request stateless -> we don't have same session ID
//        http.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    }

    // Creating UserDetailsService() for creating multiple users

//    @Bean
//    public UserDetailsService userDetailsService(){
//
//        UserDetails user = User
//                .withDefaultPasswordEncoder()
//                .username("nivya")
//                .password("nivya@123")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User
//                .withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin@123")
//                .roles("ADMIN")
//                .build();
//
//
//        return new InMemoryUserDetailsManager(user, admin);
//
//    }
}