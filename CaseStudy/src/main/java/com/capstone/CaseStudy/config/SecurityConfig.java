package com.capstone.CaseStudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // authentication - the act of checking the users credentials .. meaning is the username and password correct
    // authorization - is what the user can do


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // you guys can research this if you want .. its for preventing automated bots and helps to increase the likelyhood that its a human using your site
        http.csrf(csrf -> csrf.disable());

        // this part of the configuration secures acutal URLS
        // this is the list of URLS that require authentication to the website before the user can view the URL
        // this works on the idea .. that all URLS are accessable to everyone except for the ones listed here
        // this restriction is NOT including authorization it is only for authentication
        http.authorizeHttpRequests((authorize) -> authorize
                // Require authentication for /customer/** endpoints
                .requestMatchers("/account").authenticated()
                .requestMatchers("/dashboard").authenticated()
                .requestMatchers("/user").authenticated()
                .requestMatchers("/messages").authenticated()
                .requestMatchers("/activities").authenticated()
                .requestMatchers("/search").authenticated()
                // Allow all other requests without authentication
                .anyRequest().permitAll()
        );

        // this section specifies where our login page is
        http.formLogin(formLogin -> formLogin
                // this is the URL for the login page ... this needs a controller method to listen and show the login page
                // this is alnalagous to /customer and the page is login
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                // spring security has this controller method created for us already .. and we are just configuring the URL where it submits to
                .loginProcessingUrl("/login/submit"));


        // this section is for configuing logout
        http.logout(formLogout -> formLogout
                // when the user logs out ... destroy the session the server side
                .invalidateHttpSession(true)
                // this is the acutal URL this is implemented by spring security and we are just specifying the get mapping for it
                .logoutUrl("/logout")
                // where does the user go after they have been logged out
                // this is a URL that we have implemented somewhere in our project or controllers
                .logoutSuccessUrl("/login")
                // extra security and delete these cookies when logging out
                .deleteCookies("username", "JSESSIONID"));


        return http.build();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
