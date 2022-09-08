package com.ciandt.summit.bootcamp2022.unit.handlers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.SongsController;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.BadAuthRequestException;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongResponseDTO;
import com.ciandt.summit.bootcamp2022.domains.tokens.dto.CreateAuthorizerDTO;
import com.ciandt.summit.bootcamp2022.domains.tokens.dto.CreateAuthorizerDataDTO;
import com.ciandt.summit.bootcamp2022.infra.feignclients.TokenProvider;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SongsController.class)
public class AuthorizationInterceptorTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private SongsController songsController;

    private final String TOKEN = "token";
    private final String USER = "user";

    private CreateAuthorizerDTO fakeCreateAuthorizer;
    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

    @BeforeEach
    public void setup() {
        CreateAuthorizerDataDTO createAuthorizerData = new CreateAuthorizerDataDTO(TOKEN, USER);
        fakeCreateAuthorizer = new CreateAuthorizerDTO(createAuthorizerData);

        mockHttpServletRequestBuilder = get("/api/musicas?filtro=filter");
    }

    @Test
    public void authorizeRequestTest() throws Exception {
        List<SongDTO> expected = List.of();
        SongResponseDTO response = new SongResponseDTO(expected);

        when(songsController.findSongsByNameOrArtistName("filter", 0))
                .thenReturn(ResponseEntity.ok(response));

        when(tokenProvider.createTokenAuthorizer(fakeCreateAuthorizer))
                .thenReturn(ResponseEntity.status(201).body("ok"));

        mockMvc.perform(
                    mockHttpServletRequestBuilder
                        .header("token", TOKEN)
                            .header("user", USER)
                )
                .andExpect(status().isOk())
                .andExpect((mvcResult) -> {
                    assertEquals(mvcResult.getResponse().getContentAsString(),
                            response.toString().replaceAll(" ", ""));
                });
    }

    @Test
    public void unauthorizedRequestTest() throws Exception {
        Request fakeFeignRequest = Request.create(Request.HttpMethod.GET, "", new TreeMap<>(), null, null, null);

        when(tokenProvider.createTokenAuthorizer(fakeCreateAuthorizer))
                .thenThrow(new FeignException.FeignClientException(400, "message", fakeFeignRequest, null, null));

        mockMvc.perform(
                        mockHttpServletRequestBuilder
                                .header("token", TOKEN)
                                .header("user", USER)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void tokenProviderThrowsInternalServerErrorTest() throws Exception {
        Request fakeFeignRequest = Request.create(Request.HttpMethod.GET, "", new TreeMap<>(), null, null, null);

        when(tokenProvider.createTokenAuthorizer(fakeCreateAuthorizer))
                .thenThrow(new FeignException.FeignClientException(500, "message", fakeFeignRequest, null, null));

        mockMvc.perform(
                        mockHttpServletRequestBuilder
                                .header("token", TOKEN)
                                .header("user", USER)
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void tokenProviderReturnNoContentTest() throws Exception {
        when(tokenProvider.createTokenAuthorizer(fakeCreateAuthorizer))
                .thenReturn(ResponseEntity.ok("created"));

        mockMvc.perform(
                        mockHttpServletRequestBuilder
                                .header("token", TOKEN)
                                .header("user", USER)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void authHeadersNotFoundTest() throws Exception {
        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> {
                    String responseAsString = result.getResponse().getContentAsString();

                    assertTrue(responseAsString.contains("verify the headers before send request"));
                });
    }

    @ParameterizedTest
    @MethodSource("generateRequestsWithInvalidAuthHeaders")
    public void invalidAuthHeadersTest(Map<String, String> headers) throws Exception {

        mockMvc.perform(
                    mockHttpServletRequestBuilder
                            .header("user", headers.get("user"))
                            .header("token", headers.get("token"))
                )
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();

                    assertTrue(exception instanceof BadAuthRequestException);
                    assertEquals(exception.getMessage(), "Auth headers not found: user or token are blank or null");
                })
                .andExpect(status().isBadRequest());
    }

    public static List<Map<String, String>> generateRequestsWithInvalidAuthHeaders() {
        Map<String, String> headers1 = Map.of("user", "", "token", "");
        Map<String, String> headers2 = Map.of("user", "   ", "token", "   ");
        Map<String, String> headers3 = Map.of("user", "user", "token", "   ");
        Map<String, String> headers4 = Map.of("user", "", "token", "token");

        return List.of(headers1, headers2, headers3, headers4);
    }

}
