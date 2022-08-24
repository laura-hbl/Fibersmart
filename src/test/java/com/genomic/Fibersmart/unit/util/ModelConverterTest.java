package com.genomic.Fibersmart.unit.util;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.*;
import com.genomic.Fibersmart.util.ModelConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ModelConverterTest {

    private ModelConverter modelConverter = new ModelConverter();

    @Test
    @DisplayName("Given an UserDTO, when ToUser, then result should match expected User")
    public void givenAUserDTO_whenUser_thenReturnExpectedUser() {

        UserRole userRole = new UserRole(1L, AuthorityConstants.RoleType.ROLE_ADMIN,
                Arrays.asList(new RolePermission(1L, "ROLE_ADMIN")));
        UserGroup userGroup = new UserGroup(1L, "GV", true,
                Arrays.asList(new GroupPermission(1L, "SEE_ALL_SLIDE")));
        User expectedUser = new User("JeanGV", "Jean", "Dupont", "password123",
                Arrays.asList(userRole), Arrays.asList(userGroup));

        User result = modelConverter.toUser(new UserDTO("JeanGV", "Jean", "Dupont",
                "password123", Arrays.asList(userRole), Arrays.asList()));

        assertThat(result.getPassword()).isEqualTo(expectedUser.getPassword());
        assertThat(result.getUsername()).isEqualTo(expectedUser.getUsername());
        assertThat(result.getUserRoles().contains(expectedUser.getUserRoles()));
    }
}
