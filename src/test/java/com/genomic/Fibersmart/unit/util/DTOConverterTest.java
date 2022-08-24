package com.genomic.Fibersmart.unit.util;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.*;
import com.genomic.Fibersmart.util.DTOConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

public class DTOConverterTest {

    private DTOConverter dtoConverter = new DTOConverter();

    @Test
    @DisplayName("Given an User, when ToUserDTO, then result should match expected UserDTO")
    public void givenAnUser_whenToUserDTO_thenReturnExpectedUserDTO() {

        UserRole userRole = new UserRole(1L, AuthorityConstants.RoleType.ROLE_ADMIN,
                Arrays.asList(new RolePermission(1L, "ROLE_ADMIN")));
        UserGroup userGroup = new UserGroup(1L, "GV", true,
                Arrays.asList(new GroupPermission(1L, "SEE_ALL_SLIDE")));
        UserDTO expectedUserDTO = new UserDTO("JeanGV", "Jean", "Dupont", "password123",
                Arrays.asList(userRole), Arrays.asList(userGroup));

        UserDTO result = dtoConverter.toUserDTO(new User("JeanGV", "Jean", "Dupont",
                "password123", Arrays.asList(userRole), Arrays.asList()));

        assertThat(result.getPassword()).isEqualTo(expectedUserDTO.getPassword());
        assertThat(result.getUsername()).isEqualTo(expectedUserDTO.getUsername());
        assertThat(result.getUserRoles().contains(expectedUserDTO.getUserRoles()));
    }

}
