package com.ciandt.summit.bootcamp2022.unit;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domains.userType.UserType;
import com.ciandt.summit.bootcamp2022.domains.userType.dto.UserTypeDTO;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.adapters.services.UserServiceImp;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @InjectMocks
    private UserServiceImp userServiceImp;

    private User user;
    @Mock
    private UserDTO userDTO;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findUserByPassingCorrectId() throws UserNotFoundException {
        Mockito.when(userRepositoryPort.findById(Mockito.any())).thenReturn(user);

        User result = userRepositoryPort.findById(Mockito.any());

        Assertions.assertNotNull(result);
    }

    @Test
    public void userDoesNotExistInTheDatabase() throws UserNotFoundException{
        Mockito.when(userRepositoryPort.findById(Mockito.any())).thenThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () ->{
            userRepositoryPort.findById(Mockito.any());
        });
    }

}
