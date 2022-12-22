package com.lunatech.assessment.imdb.security;

import static com.lunatech.assessment.imdb.constants.ImdbSecurityConstants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration{

    private static final String DEGREE_URL_PATTERN = "/degree/**";
    private static final String NAME_URL_PATTERN = "/name/**";
    private static final String TITLE_URL_PATTERN = "/title/**";
    @Autowired
    @Qualifier("customerAuthenticationEntryPoint")
    AuthenticationEntryPoint customAuthenticationEntrypoint; 
    
    @Bean 
    public InMemoryUserDetailsManager userDetailService(PasswordEncoder passwordEndcoder){
                
        UserDetails titleUser = User.withUsername(USER_ROLE_TITLE)
            .password(passwordEndcoder.encode(PASS_ROLE_TITLE))
            .roles(ROLE_TITLE)
            .build(); 

        UserDetails degreeUser = User.withUsername(USER_ROLE_DEGREE)
            .password(passwordEndcoder.encode(PASS_ROLE_DEGREE))
            .roles(ROLE_DEGREE)
            .build(); 

        UserDetails nameUser = User.withUsername(USER_ROLE_NAME)
            .password(passwordEndcoder.encode(PASS_ROLE_NAME))
            .roles(ROLE_NAME)
            .build(); 

        UserDetails titleNameUser = User.withUsername(USER_ROLE_TITLE_NAME)
            .password(passwordEndcoder.encode(PASS_ROLE_TITLE_NAME))
            .roles(ROLE_TITLE, ROLE_NAME)
            .build(); 

        UserDetails titleNameDegreeUser = User.withUsername(USER_ROLE_TITLE_NAME_DEGREE)
            .password(passwordEndcoder.encode(PASS_ROLE_TITLE_NAME_DEGREE))
            .roles(ROLE_TITLE, ROLE_NAME,ROLE_DEGREE)
            .build(); 
            
        UserDetails noRoleUser = User.withUsername(USER_ROLE_NO)
            .password(passwordEndcoder.encode(PASS_ROLE_NO))
            .roles(ROLE_NO)
            .build();              

        return new InMemoryUserDetailsManager(titleUser, degreeUser, nameUser, titleNameUser, titleNameDegreeUser, noRoleUser); 
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
            .requestMatchers(TITLE_URL_PATTERN).hasRole(ROLE_TITLE)
            .requestMatchers(NAME_URL_PATTERN).hasRole(ROLE_NAME)
            .requestMatchers(DEGREE_URL_PATTERN).hasRole(ROLE_DEGREE)
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic();
            // .and()
            // .exceptionHandling()
            // .authenticationEntryPoint(customAuthenticationEntrypoint); 
        
        return http.build(); 
    }

    @Bean 
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
    }

}
