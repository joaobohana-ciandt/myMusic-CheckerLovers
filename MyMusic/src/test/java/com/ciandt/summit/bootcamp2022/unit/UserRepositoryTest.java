package com.ciandt.summit.bootcamp2022.unit;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.UserEntity;
import com.ciandt.summit.bootcamp2022.infra.adapters.repositories.SpringUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class UserRepositoryTest {
    @MockBean
    private SpringUserRepository springUserRepository;

    @MockBean
    private UserRepositoryPort userRepositoryPort;

    private UserEntity userEntity;

    private User user;

    private final String ID = "jhjh1222";

    @BeforeEach
    public void setup(){
        userEntity = new UserEntity();
        userEntity.setId(ID);
        userEntity.setName("Antony");
    }

    @Test
    public void UserFound() throws UserNotFoundException {
        when(springUserRepository.findById(ID))
                .thenReturn(Optional.of(userEntity));
        User result = userRepositoryPort.findById(ID);
        assertEquals(result, user);
    }

    @Test
    public void UserNotFound() throws UserNotFoundException{
        String expectedExceptionMessage = "Specified user was not found";

        when(springUserRepository.findById(ID))
                .thenReturn(Optional.empty());
        assertNull(userRepositoryPort.findById(ID));
    }
}
