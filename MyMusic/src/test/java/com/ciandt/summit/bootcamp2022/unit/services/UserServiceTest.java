package com.ciandt.summit.bootcamp2022.unit.services;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.domains.artists.Artist;
import com.ciandt.summit.bootcamp2022.domains.artists.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.userType.UserType;
import com.ciandt.summit.bootcamp2022.domains.userType.dto.UserTypeDTO;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.adapters.services.UserServiceImp;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class UserServiceTest {

    public static final String ID = "hhjj";
    public static final String NAME = "antonio";

    @MockBean
    private UserRepositoryPort userRepositoryPort;
    @Autowired
    private UserServicePort userServicePort;

    private final static User USER_FROM_REPO = new User();

    private final static UserDTO userDTO = new UserDTO();
    @BeforeEach
    public void setup() {
        Song song = new Song("1", "paradise", new Artist("2", "CloudPlay",new ArrayList<Song>()));
        ArrayList<Song> listSong = new ArrayList<Song>();
        listSong.add(song);
        UserType userType = new UserType("1", "premium");
        Playlist playlist = new Playlist("1", listSong);
        USER_FROM_REPO.setId(ID);
        USER_FROM_REPO.setUserType(userType);
        USER_FROM_REPO.setPlaylist(playlist);
        USER_FROM_REPO.setName(NAME);

        userDTO.setUserTypeDTO(userType.toDTO());
        userDTO.setId(ID);
        userDTO.setName(NAME);
        userDTO.setPlaylistDTO(playlist.toPlaylistDTO());

    }

    @Test
    void whenFindByIdReturnAnUserInstance() throws UserNotFoundException {

       when(userRepositoryPort.findById(Mockito.any())).thenReturn(USER_FROM_REPO);
       UserDTO response = userServicePort.findById(ID);
       Assertions.assertEquals(UserDTO.class, response.getClass()) ;
    }

    @Test
    public void userDoesNotExistInTheDatabase() throws UserNotFoundException{
        when(userRepositoryPort.findById(Mockito.any())).thenThrow(UserNotFoundException.class);

        Assertions.assertThrows(UserNotFoundException.class, () ->{
            userRepositoryPort.findById(Mockito.any());
        });
    }

}
