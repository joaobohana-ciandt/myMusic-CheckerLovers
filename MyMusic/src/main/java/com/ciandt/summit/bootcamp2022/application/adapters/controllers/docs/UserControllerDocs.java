package com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserControllerDocs {

    @ApiOperation(value = "Find user by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = ""),
            @ApiResponse(code = 401, message = ""),
            @ApiResponse(code = 400, message = "Specified user was not found")})
    ResponseEntity<UserDTO> findUserById(@PathVariable String userId) throws UserNotFoundException;
}
