package com.genomic.Fibersmart.controller;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.dto.PasswordDTO;
import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.User;
import com.genomic.Fibersmart.service.IUserService;
import com.genomic.Fibersmart.util.DTOConverter;
import com.genomic.Fibersmart.util.ModelConverter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Creates REST endpoints for crud operations on {@link User} data.
 *
 * @see IUserService
 */
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private IUserService userService;

    private DTOConverter dtoConverter;

    private ModelConverter modelConverter;

    @Autowired
    public UserController(final IUserService userService, final DTOConverter dtoConverter,
                          final ModelConverter modelConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    public UserController() {
    }

    /**
     * Registers a new {@link User}.
     *
     * @param userDTO the user to be added
     * @return ResponseEntity object which holds the created user and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_USER')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody final UserDTO userDTO) {
        LOGGER.debug("POST Request on /users with username {}", userDTO.getUsername());

        User user = userService.createUser(modelConverter.toUser(userDTO));
        UserDTO createdUser = dtoConverter.toUserDTO(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Deletes a {@link User}.
     *
     * @param userId id of the {@link User} to delete.
     */
    @PreAuthorize("hasAuthority('MANAGE_USER')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") final Long userId) {
        LOGGER.debug("DELETE Request on /users/{id} with id : {}", userId);

        userService.deleteUser(userId);
    }

    /**
     * Updates a registered {@link User}.
     *
     * @param userId  id of the user to be updated
     * @param userDTO the user to be updated
     * @return ResponseEntity object which holds the updated user and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") final Long userId,
                                              @RequestBody final UserDTO userDTO) {
        LOGGER.debug("PUT Request on /users/{id} with id : {}", userId);

        User userToUpdate = modelConverter.toUser(userDTO);
        userToUpdate.setId(userId);

        User userUpdated = userService.updateUser(userToUpdate);

        return new ResponseEntity<>(dtoConverter.toUserDTO(userUpdated), HttpStatus.OK);
    }

    /**
     * Retrieves all users.
     *
     * @return ResponseEntity object which holds the user list and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_USER')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUser() {
        LOGGER.debug("GET Request on /users");

        List<UserDTO> users = userService.getAllUser().stream()
                .map(user -> dtoConverter.toUserDTO(user))
                .collect(Collectors.toList());

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Updates the user password.
     *
     * @param userId   id of the user which password will be updated
     * @param password object which holds current and new password
     * @return ResponseEntity object that contains the user whose password is updated and Http status generated.
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<UserDTO> changePassword(@PathVariable("id") final Long userId,
                                                  @RequestBody final PasswordDTO password) {
        LOGGER.debug("PUT Request on /users/{id}/password with id : {}", userId);

        // Retrieves current logged in user
        User loggedInUser = userService.getLoggedInUser();

        // Checks user permissions
        List<AuthorityConstants.Permission> loggedInUserPermissions = userService.getUserPermissions(loggedInUser);

        // User can't change password if he is not the current logged in user or if he doesn't have the right to do so
        if (loggedInUser.getId() == userId || loggedInUserPermissions.contains(AuthorityConstants.Permission.MANAGE_USER)) {
            User user = userService.changePassword(userId, password);

            return new ResponseEntity<>(dtoConverter.toUserDTO(user), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }
}
