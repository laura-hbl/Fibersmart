package com.genomic.Fibersmart.controller;

import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private IUserService userService;

    @Autowired
    public UserController(final IUserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO userDTO) {
        LOGGER.debug("POST Request on /user/add with username {}", userDTO.getUsername());

        UserDTO userAdded = userService.addUser(userDTO);

        LOGGER.info("POST Request on /user/add with username {} - SUCCESS");
        return new ResponseEntity<>(userAdded, HttpStatus.CREATED);
    }
}


