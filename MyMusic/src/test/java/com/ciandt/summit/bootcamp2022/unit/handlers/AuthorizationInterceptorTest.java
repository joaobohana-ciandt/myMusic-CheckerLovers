package com.ciandt.summit.bootcamp2022.unit.handlers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.MusicController;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.BadAuthRequestException;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.token.dto.CreateAuthorizerDTO;
import com.ciandt.summit.bootcamp2022.domains.token.dto.CreateAuthorizerDataDTO;
import com.ciandt.summit.bootcamp2022.infra.feignclients.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: refinar testes após a conclusão da feature 812

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MusicController.class)
public class AuthorizationInterceptorTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private MusicController musicController;

    private final String TOKEN = "token";
    private final String USER = "user";

    private CreateAuthorizerDTO fakeCreateAuthorizer;

    @BeforeEach
    public void setup() {
        CreateAuthorizerDataDTO createAuthorizerData = new CreateAuthorizerDataDTO(TOKEN, USER);
        fakeCreateAuthorizer = new CreateAuthorizerDTO(createAuthorizerData);
    }

    @Test
    public void authorizeRequestTest() throws Exception {
        List<SongDTO> expected = List.of();

        when(musicController.findSongsByNameOrArtistName("filter"))
                .thenReturn(ResponseEntity.ok(expected));

        when(tokenProvider.createTokenAuthorizer(fakeCreateAuthorizer))
                .thenReturn(ResponseEntity.status(201).body("ok"));

        mockMvc.perform(
                    get("/api/musicas?filtro=filter")
                        .header("token", TOKEN)
                            .header("user", USER)
                )
                .andExpect(status().isOk())
                .andExpect((mvcResult) -> {
                    assertTrue(mvcResult.getResponse().getContentAsString().equals("[]"));
                });
    }

    @Test
    public void authHeadersNotFoundTest() throws Exception {
        mockMvc.perform(get("/api/musicas?filtro=filter"))
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();

                    assertTrue(exception instanceof BadAuthRequestException);
                    assertEquals(exception.getMessage(), "Auth headers not found: user or token are blank or null");
                })
                .andExpect(status().isBadRequest());
    }

    @Test
    public void unauthorizedRequestTest() throws Exception {
        when(tokenProvider.createTokenAuthorizer(fakeCreateAuthorizer))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token expirado"));

        mockMvc.perform(
                    get("/api/musicas?filtro=filter")
                            .header("token", TOKEN)
                            .header("user", USER)
                )
                .andExpect(status().isUnauthorized());
    }
}
