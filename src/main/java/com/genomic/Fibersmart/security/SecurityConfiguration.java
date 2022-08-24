package com.genomic.Fibersmart.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genomic.Fibersmart.security.jwt.AuthTokenFilter;
import com.genomic.Fibersmart.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Application security configuration class.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    private final ObjectMapper objectMapper;

    UserDetailsService userDetailsService;

    private final JwtUtils jwtUtils;


    @Autowired
    public SecurityConfiguration(final UserDetailsService userDetailsService, final ObjectMapper objectMapper,
                                 final JwtUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.jwtUtils = jwtUtils;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
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
     * Allows configuring web based security for specific http requests.
     *
     * @param http HttpSecurity
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(this::loginSuccessHandler)
                .failureHandler(this::loginFailureHandler)
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessHandler(this::logoutSuccessHandler)
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(this::authenticationEntryPointHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Handles authentication success. A token is created, then added in a cookie which will be secure.
     *
     * @param response       HttpServletResponse object
     * @param request        HttpServletRequest object
     * @param authentication Authentication object
     */
    private void loginSuccessHandler(final HttpServletRequest request, final HttpServletResponse response,
                                     final Authentication authentication) throws IOException {

        // Generates a json web token
        String bearer = "Bearer " + jwtUtils.generateJwtToken(authentication);
        response.addHeader(HttpHeaders.AUTHORIZATION, bearer);
        response.setStatus(HttpStatus.OK.value());

        objectMapper.writeValue(response.getWriter(), "You are now logged in to Fibersmart!");
    }

    /**
     * Handles login failure.
     *
     * @param response HttpServletResponse object
     * @param request  HttpServletRequest object
     * @param e        AuthenticationException object
     */
    private void loginFailureHandler(final HttpServletRequest request, final HttpServletResponse response,
                                     final AuthenticationException e) throws IOException {

        LOGGER.info("onLoginFailureHandler", e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), "The username or password you entered is incorrect.");
    }

    /**
     * Handles logout success and invalidates the cookie.
     *
     * @param response       HttpServletResponse object
     * @param request        HttpServletRequest object
     * @param authentication Authentication object
     */
    private void logoutSuccessHandler(final HttpServletRequest request, final HttpServletResponse response,
                                      final Authentication authentication) throws IOException {

        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), "You have successfully logged out!");
    }

    /**
     * Handles authenticationEntryPoint.
     *
     * @param response HttpServletResponse object
     * @param request  HttpServletRequest object
     * @param e        AuthenticationException object
     */
    private void authenticationEntryPointHandler(final HttpServletRequest request, final HttpServletResponse response,
                                                 final AuthenticationException e) throws IOException {

        response.sendError(HttpStatus.UNAUTHORIZED.value());
        objectMapper.writeValue(response.getWriter(), "Access Denied");
    }
}
