package com.genomic.Fibersmart.controller;

import com.genomic.Fibersmart.dto.LoginDTO;
import com.genomic.Fibersmart.security.MyUserDetailsService;
import javax.validation.Valid;
import com.genomic.Fibersmart.security.jwt.JwtUtils;
import com.genomic.Fibersmart.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * Time in seconds before the cookie expires.
     */
    @Value("${fibersmart.app.cookieExpirationSec}")
    private int cookieExpirationSec;

    /**
     * IUserService's implement class reference.
     */
    private final IUserService userService;

    /**
     * JwtUtils instance.
     */
    private JwtUtils jwtUtils;

    /**
     * AuthenticationManager's implement class reference.
     */
    private AuthenticationManager auth;

    /**
     * MyUserDetailsService instance.
     */
    private MyUserDetailsService myUserDetailsService;

    /**
     * Constructor of class CurvePointController.
     * Initialize userService, jwtUtils, auth, myUserDetailsService.
     *
     * @param userService          IUserService's implement class reference
     * @param jwtUtils             JwtUtils instance
     * @param auth                 AuthenticationManager's implement class reference
     * @param myUserDetailsService MyUserDetailsService instance
     */
    @Autowired
    public LoginController(final IUserService userService, final JwtUtils jwtUtils, final AuthenticationManager auth,
                           final MyUserDetailsService myUserDetailsService) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.auth = auth;
        this.myUserDetailsService = myUserDetailsService;
    }

    /**
     * Authenticates an user and provides a Token.
     *
     * @param loginDTO user's login credentials
     * @param response HttpServletResponse instance
     * @return The reference to the login HTML page if result has no errors. Else, redirects to /bidList/list endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<LoginDTO> authenticateUser(@Valid final LoginDTO loginDTO, final HttpServletResponse response) {
        LOGGER.debug("POST Request on /login from user : {}", loginDTO.getUsername());

        // Authenticates the user
        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        // Generates a json web token
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Creates a cookie and secures it
        Cookie cookie = new Cookie("Token", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(cookieExpirationSec);
        response.addCookie(cookie);

        LOGGER.info("POST Request on /login - SUCCESS");
        return new ResponseEntity<>(loginDTO, HttpStatus.ACCEPTED);
    }
}
