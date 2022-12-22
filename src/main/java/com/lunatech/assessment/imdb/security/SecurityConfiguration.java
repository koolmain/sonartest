package com.lunatech.assessment.imdb.security;


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

    @Autowired
    @Qualifier("customerAuthenticationEntryPoint")
    AuthenticationEntryPoint customAuthenticationEntrypoint; 
    
    @Bean 
    public InMemoryUserDetailsManager userDetailService(PasswordEncoder passwordEndcoder){
                
        UserDetails titleUser = User.withUsername("userT")
            .password(passwordEndcoder.encode("passwordT"))
            .roles("TITLE")
            .build(); 

        UserDetails degreeUser = User.withUsername("userD")
            .password(passwordEndcoder.encode("passwordD"))
            .roles("DEGREE")
            .build(); 

        UserDetails nameUser = User.withUsername("userN")
            .password(passwordEndcoder.encode("passwordN"))
            .roles("NAME")
            .build(); 

        UserDetails titleNameUser = User.withUsername("userTN")
            .password(passwordEndcoder.encode("passwordTN"))
            .roles("TITLE","NAME")
            .build(); 

        UserDetails titleNameDegreeUser = User.withUsername("userTND")
            .password(passwordEndcoder.encode("passwordTND"))
            .roles("TITLE","NAME","DEGREE")
            .build(); 
            
        UserDetails noRoleUser = User.withUsername("usernr")
            .password(passwordEndcoder.encode("passwordnr"))
            .roles("NOROLE")
            .build();              

        return new InMemoryUserDetailsManager(titleUser, degreeUser, nameUser, titleNameUser, titleNameDegreeUser, noRoleUser); 
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
            .requestMatchers("/title/**").hasRole("TITLE")
            .requestMatchers("/name/**").hasRole("NAME")
            .requestMatchers("/degree/**").hasRole("DEGREE")
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
