package com.genomic.Fibersmart.integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genomic.Fibersmart.model.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private MvcResult resultAdmin;

    private String bearerAdmin;

    private MvcResult resultStandard;

    private String bearerStandard;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        resultAdmin = mvc.perform(formLogin("/login").user("admin").password("admin"))
                .andReturn();
        bearerAdmin = resultAdmin.getResponse().getHeader("Authorization");

        resultStandard = mvc.perform(formLogin("/login").user("standard").password("standard"))
                .andReturn();
        bearerStandard = resultStandard.getResponse().getHeader("Authorization");
    }

    @Test
    @Tag("Login")
    public void givenValidCredentials_whenLogin_thenReturnOkStatus() throws Exception {

        MvcResult result = mvc.perform(formLogin("/login").user("admin").password("admin"))
                .andExpect(status().isOk())
                .andReturn();

        bearerAdmin = result.getResponse().getHeader("Authorization");

        String str = result.getResponse().getContentAsString();
        assertTrue(str.contains("You are now logged in to Fibersmart!"));
    }

    @Test
    @Tag("Login")
    public void givenInvalidCredentials_whenLogin_thenReturnUnauthorizedStatus() throws Exception {

        MvcResult result = mvc.perform(formLogin("/login").user("admin").password("foo"))
                .andExpect(status().isUnauthorized())
                .andReturn();

        bearerAdmin = result.getResponse().getHeader("Authorization");

        String str = result.getResponse().getContentAsString();
        assertTrue(str.contains("The username or password you entered is incorrect."));
    }

    @Test
    @Tag("GetAllUSer")
    public void givenALoggedInAdminUser_whenGetAllUser_thenReturnOkStatus() throws Exception {

        mvc.perform(get("/user/all")
                        .header("Authorization", bearerAdmin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @Tag("GetAllUSer")
    public void givenALoggedInStandardUser_whenGetAllUser_thenReturnForbiddenStatus() throws Exception {

        mvc.perform(get("/user/all")
                        .header("Authorization", bearerStandard)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Tag("CreateUSer")
    public void givenALoggedInAdminUser_whenCreateUser_thenReturnCreatedStatus() throws Exception {

        User userToAdd = new User("test", "test", "test", "test",
                null, null);

        MvcResult result = mvc.perform(post("/user/register")
                        .header("Authorization", bearerAdmin)
                        .content(mapper.writeValueAsString(userToAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @Tag("CreateUSer")
    public void givenALoggedInStandardUser_whenCreateUSer_thenReturnForbiddenStatus() throws Exception {

        User userToAdd = new User("test", "test", "test", "test",
                null, null);

        mvc.perform(post("/user/register")
                        .header("Authorization", bearerStandard)
                        .content(mapper.writeValueAsString(userToAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

    }

    @Test
    @Tag("DeleteUSer")
    public void givenALoggedInAdminUser_whenDeleteUSer_thenReturnOkStatus() throws Exception {

        mvc.perform(get("/user/delete/2")
                        .header("Authorization", bearerAdmin)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("DeleteUSer")
    public void givenALoggedInStandardUser_whenDeleteUSer_thenReturnForbiddenStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(formLogin("/login").user("standard").password("standard"))
                .andReturn();

        String bearer = mvcResult.getResponse().getHeader("Authorization");

        mvc.perform(get("/user/delete/2")
                        .header("Authorization", bearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @Tag("UpdateUser")
    public void givenALoggedInAdminUser_whenUpdateUser_thenReturnOkStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(formLogin("/login").user("admin").password("admin"))
                .andReturn();

        String bearer = mvcResult.getResponse().getHeader("Authorization");

        User userToUpdate= new User(2L,"standard", "update", "update", "update",
                null, null);

        mvc.perform(post("/user/update/2")
                        .header("Authorization", bearer)
                        .content(mapper.writeValueAsString(userToUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    @Tag("UpdateUser")
    public void givenALoggedInStandardUser_whenUpdateUser_thenReturnForbiddenStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(formLogin("/login").user("standard").password("standard"))
                .andReturn();

        String bearer = mvcResult.getResponse().getHeader("Authorization");

        User userToUpdate= new User("update", "update", "update", "standard",
                null, null);


        mvc.perform(post("/user/update/2")
                        .content(mapper.writeValueAsString(userToUpdate))
                        .header("Authorization", bearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}