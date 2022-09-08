package com.ciandt.summit.bootcamp2022.unit;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.UserController;
import com.ciandt.summit.bootcamp2022.application.adapters.controllers.handlers.AuthorizationInterceptor;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.tokens.dto.CreateAuthorizerDTO;
import com.ciandt.summit.bootcamp2022.domains.tokens.dto.CreateAuthorizerDataDTO;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationInterceptor authorizationInterceptor;

    @MockBean
    private UserServicePort userServicePort;

    private final String USER = "user";
    private final String TOKEN = "token";

    private final String USER_ID = "USER_ID";
    private final String USER_NAME = "USER_NAME";

    private CreateAuthorizerDTO fakeCreateAuthorizer;

    @BeforeEach
    public void setupAuthorizer() throws Exception {
        CreateAuthorizerDataDTO createAuthorizerData = new CreateAuthorizerDataDTO(TOKEN, USER);
        fakeCreateAuthorizer = new CreateAuthorizerDTO(createAuthorizerData);
        when(authorizationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(true);
    }

    @Test
    public void findUserByIdPassingValidUserId() throws Exception {
        when(userServicePort.findById(USER_ID)).
                thenReturn(new UserDTO(USER_ID, USER_NAME, null, null));

        MvcResult response = mockMvc
                .perform(get("/users/{userId}", USER_ID)
                        .header("token", TOKEN)
                        .header("user", USER)).andReturn();

        UserDTO expectedUser = new UserDTO(USER_ID, USER_NAME, null, null);

        String expectedResponseAsString = expectedUser.toString().replaceAll(" ", "");

        String actualResponseAsString = response.getResponse().getContentAsString().replaceAll(" ", "");

        assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
        assertEquals(expectedResponseAsString, actualResponseAsString);
    }

    @Test
    public void findUserByIdPassingInvalidUserId() throws Exception {
        when(userServicePort.findById("INVALID_ID"))
                .thenThrow(new UserNotFoundException("Specified user was not found."));

        MvcResult response = mockMvc
                .perform(get("/users/{userId}", "INVALID_ID")
                        .header("token", TOKEN)
                        .header("user", USER)).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getResponse().getStatus());
    }

}
