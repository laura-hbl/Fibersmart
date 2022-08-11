package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.repository.UserRepository;
import com.genomic.Fibersmart.util.DTOConverter;
import com.genomic.Fibersmart.util.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final DTOConverter dtoConverter;

    private final ModelConverter modelConverter;

    @Autowired
    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder passwordEncoder,
                       final DTOConverter dtoConverter, final ModelConverter modelConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    @Override
    public UserDTO addUser (UserDTO userDTO) {

    }
}
