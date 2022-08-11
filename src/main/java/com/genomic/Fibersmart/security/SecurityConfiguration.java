package com.genomic.Fibersmart.security;

import com.genomic.Fibersmart.security.jwt.AuthTokenFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    /**
     * UserDetailsService instance.
     */
    UserDetailsService userDetailsService;

    /**
     * Constructor of class SecurityConfiguration.
     * Initialize userDetailsService.
     *
     * @param userDetailsService UserDetailsService instance
     */
    @Autowired
    public SecurityConfiguration(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Creates an instance of AuthTokenFilter.
     *
     * @return AuthTokenFilter instance
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * Configures the AuthenticationManagerBuilder to use the password encoder and the implementation of
     * UserDetailsService for configuring DaoAuthenticationProvider.
     *
     * @param authenticationManagerBuilder AuthenticationManagerBuilder instance
     */
    @Override
    public void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Overrides authenticationManagerBean() method so the authentication manager is available in app context.
     *
     * @return AuthenticationManager reference
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Creates an instance of BCryptPasswordEncoder in order to encrypt the password.
     *
     * @return BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Allows configuring web based security for specific http requests.
     *
     * @param http HttpSecurity
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        // For each api the filter has been added with above http request condition
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
