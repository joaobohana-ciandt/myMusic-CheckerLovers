package com.ciandt.summit.bootcamp2022.application.adapters.controllers.handlers;

import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.BadAuthRequestException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.domains.tokens.dto.CreateAuthorizerDTO;
import com.ciandt.summit.bootcamp2022.domains.tokens.dto.CreateAuthorizerDataDTO;
import com.ciandt.summit.bootcamp2022.infra.feignclients.TokenProvider;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String user = request.getHeader("user");
        String token = request.getHeader("token");

        boolean userInvalid = user == null || user.isBlank();
        boolean tokenInvalid = token == null || token.isBlank();

        if (userInvalid || tokenInvalid) {
            throw new BadAuthRequestException("Auth headers not found: user or token are blank or null");
        }

        CreateAuthorizerDataDTO createAuthorizerData = new CreateAuthorizerDataDTO(token, user);
        CreateAuthorizerDTO createAuthorizer = new CreateAuthorizerDTO(createAuthorizerData);

        ResponseEntity<String> tokenProviderResponse;

        try {
            tokenProviderResponse = tokenProvider.createTokenAuthorizer(createAuthorizer);
        } catch (FeignException.FeignClientException e) {
            if (e.status() == 400) {
                throw new UnauthorizedException("User unauthorized");
            }

            throw e;
        }

        return tokenProviderResponse.getBody().equals("ok");
    }
}
