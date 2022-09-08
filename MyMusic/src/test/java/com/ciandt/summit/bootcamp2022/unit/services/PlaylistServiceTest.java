package com.ciandt.summit.bootcamp2022.unit.services;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.domains.artists.Artist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistSongLimitExceededException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.repositories.PlaylistRespositoryPort;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.repositories.SongRepositoryPort;
import com.ciandt.summit.bootcamp2022.domains.userType.UserType;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.PlaylistEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class PlaylistServiceTest {
    @MockBean
    private PlaylistRespositoryPort playlistRespositoryPort;

    @MockBean
    private SongRepositoryPort songRepositoryPort;

    @MockBean
    private UserRepositoryPort userRepositoryPort;

    @Autowired
    private PlaylistServicePort playlistServicePort;

    @Autowired
    private UserServicePort userServicePort;

    private final static List<Song> SONGS_FROM_REPO = new ArrayList<>();
    private final static List<Playlist> PLAYLISTS_FROM_REPO = new ArrayList<>();
    private final static Playlist PLAYLISTEMPTY = new Playlist();
    private final static User USER_FROM_REPO = new User();
    private final static User USER_FROM_REPO_COMMON = new User();

    @BeforeAll
    static void setup() {
        Artist artist = new Artist(UUID.randomUUID().toString(), "Fake Artist", new ArrayList<>());

        List.of("About A Girl", "About A Boy", "About A Dog").forEach(name -> {
            Song song = new Song(UUID.randomUUID().toString(), name, artist);
            SONGS_FROM_REPO.add(song);
        });

        List<Song> playlistSongs = new ArrayList<>(SONGS_FROM_REPO);

        Playlist playlist = new Playlist(UUID.randomUUID().toString(), playlistSongs);
        PLAYLISTS_FROM_REPO.add(playlist);

        for (int i = 0; i < 7; i++) {
            Song song = new Song(UUID.randomUUID().toString(), "Fake Song " + 1 + i, artist);
            SONGS_FROM_REPO.add(song);
        }

        for (int i = 0; i < 4; i++) {
            playlist = new Playlist(UUID.randomUUID().toString(), new ArrayList<>());
            PLAYLISTS_FROM_REPO.add(playlist);
        }

        USER_FROM_REPO.setId("hh11");
        USER_FROM_REPO.setPlaylist(playlist);
        USER_FROM_REPO.setUserType(new UserType("hh11", "premium"));

        USER_FROM_REPO_COMMON.setId("hh22");
        USER_FROM_REPO_COMMON.setPlaylist(playlist);
        USER_FROM_REPO_COMMON.setUserType(new UserType("hh22", "comum"));
    }

    @Test
    void addSongsToPlaylistPassingValidListOfSongs() throws PlaylistsNotFoundException, SongsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException, PlaylistSongLimitExceededException {
        String id = PLAYLISTS_FROM_REPO.get(2).getId();
        List<SongDTO> songs = new ArrayList<>();
        songs.add(SONGS_FROM_REPO.get(0).toDTO());
        songs.add(SONGS_FROM_REPO.get(1).toDTO());
        songs.add(SONGS_FROM_REPO.get(2).toDTO());

        when(playlistRespositoryPort.findById(id)).thenReturn(PLAYLISTS_FROM_REPO.get(2));
        when(userRepositoryPort.findById("hh11")).thenReturn(USER_FROM_REPO);
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(0).getId())).thenReturn(SONGS_FROM_REPO.get(0));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(1).getId())).thenReturn(SONGS_FROM_REPO.get(1));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(2).getId())).thenReturn(SONGS_FROM_REPO.get(2));

        playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);

        Playlist playlist = playlistRespositoryPort.findById(id);

        assertEquals(3, playlist.getSongs().size());
    }

    @Test
    void addSongsToPlaylistPassingInvalidListOfSongs() throws PlaylistsNotFoundException, SongsNotFoundException {
        String id = PLAYLISTS_FROM_REPO.get(1).getId();
        List<SongDTO> songs = new ArrayList<>();
        songs.add(SONGS_FROM_REPO.get(1).toDTO());
        Song song = new Song(UUID.randomUUID().toString(), "name", new Artist(UUID.randomUUID().toString(), "Fake Artist 2", new ArrayList<>()));
        songs.add(song.toDTO());

        when(playlistRespositoryPort.findById(id)).thenReturn(PLAYLISTS_FROM_REPO.get(1));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(1).getId())).thenReturn(SONGS_FROM_REPO.get(1));
        when(songRepositoryPort.findById(song.getId())).thenThrow(new SongsNotFoundException("Specified song was not found."));


        SongsNotFoundException thrown = assertThrows(SongsNotFoundException.class, () -> {
            playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);
        });

        assertEquals("Specified song was not found.", thrown.getMessage());
    }

    @Test
    void addSongsToNonExistentPlaylist() throws SongsNotFoundException, PlaylistsNotFoundException {
        String id = "NOT FOUND";
        List<SongDTO> songs = new ArrayList<>();

        when(playlistRespositoryPort.findById(id)).thenThrow(new PlaylistsNotFoundException("Specified playlist was not found"));

        PlaylistsNotFoundException thrown = assertThrows(PlaylistsNotFoundException.class, () -> {
            playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);
        });

        assertEquals("Specified playlist was not found", thrown.getMessage());
    }

    @Test
    void cannotAddDuplicatedSongToPlaylistTest() throws Exception {
        String id = "ANY";
        String exceptionMessageExpected = "Cannot add duplicate song(s) to playlist";
        List<SongDTO> songs = List.of(SONGS_FROM_REPO.get(0), SONGS_FROM_REPO.get(0))
                .stream().map(Song::toDTO)
                .collect(Collectors.toList());

        when(playlistRespositoryPort.findById(id))
                .thenReturn(PLAYLISTS_FROM_REPO.get(0));

        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(0).getId()))
                .thenReturn(SONGS_FROM_REPO.get(0));

        DuplicatedSongInPlaylist exception = assertThrows(DuplicatedSongInPlaylist.class, () -> {
            playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);
        });

        assertEquals(exception.getMessage(), exceptionMessageExpected);
    }

    @Test
    void removeExistingSongFromPlaylist() throws PlaylistsNotFoundException, SongsNotFoundException {
        Playlist playlist = PLAYLISTS_FROM_REPO.get(0);
        Song songToRemove = playlist.getSongs().get(0);

        String playlistId = playlist.getId();
        String firstSongId = songToRemove.getId();

        List<Song> playlistExpectedSongs = playlist.getSongs().subList(1, playlist.getSongs().size());
        Playlist playlistExpected = new Playlist(playlistId, playlistExpectedSongs);
        PlaylistEntity playlistEntity = new PlaylistEntity(playlistExpected);

        when(playlistRespositoryPort.findById(playlistId)).thenReturn(playlist);
        when(songRepositoryPort.findById(songToRemove.getId())).thenReturn(songToRemove);
        when(playlistRespositoryPort.addSong(playlistEntity)).thenReturn(playlistExpected);

        Playlist playlistResult = assertDoesNotThrow(() -> playlistServicePort.removeSongFromPlaylist(playlistId, firstSongId));

        assertFalse(playlist.getSongs().contains(songToRemove));
        assertEquals(playlistExpected, playlistResult);
    }

    @Test
    void removeNonExistentSongFromPlaylist() throws PlaylistsNotFoundException, SongsNotFoundException {
        Playlist playlist = PLAYLISTS_FROM_REPO.get(0);
        Song songToRemove = playlist.getSongs().get(0);

        String playlistId = playlist.getId();
        String songToRemoveId = songToRemove.getId();

        String exceptionMessageExpected = "Specified song was not found.";

        when(playlistRespositoryPort.findById(playlistId)).thenReturn(playlist);
        when(songRepositoryPort.findById(songToRemoveId)).thenThrow(new SongsNotFoundException("Specified song was not found."));

        SongsNotFoundException exception = assertThrows(
                SongsNotFoundException.class, () -> playlistServicePort.removeSongFromPlaylist(playlistId, songToRemoveId)
        );

        assertEquals(exception.getMessage(), exceptionMessageExpected);
    }

    @Test
    public void removeSongNonIncludedInPlaylist() throws PlaylistsNotFoundException, SongsNotFoundException {
        Playlist playlist = PLAYLISTS_FROM_REPO.get(0);
        Song songToRemove = SONGS_FROM_REPO.get(3);

        String playlistId = playlist.getId();
        String songToRemoveId = songToRemove.getId();

        String exceptionMessageExpected = "Specified song was not found in playlist";

        when(playlistRespositoryPort.findById(playlistId)).thenReturn(playlist);
        when(songRepositoryPort.findById(songToRemoveId)).thenReturn(songToRemove);

        SongsNotFoundException exception = assertThrows(
                SongsNotFoundException.class, () -> playlistServicePort.removeSongFromPlaylist(playlistId, songToRemoveId)
        );

        assertEquals(exception.getMessage(), exceptionMessageExpected);
    }
    @Test
    void addSongsToPlaylistwithUserCommon() throws PlaylistsNotFoundException, SongsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException, PlaylistSongLimitExceededException {
        String id = PLAYLISTS_FROM_REPO.get(2).getId();
        List<SongDTO> songs = new ArrayList<>();
        songs.add(SONGS_FROM_REPO.get(0).toDTO());
        songs.add(SONGS_FROM_REPO.get(1).toDTO());
        songs.add(SONGS_FROM_REPO.get(2).toDTO());

        when(playlistRespositoryPort.findById(id)).thenReturn(PLAYLISTS_FROM_REPO.get(2));
        when(userRepositoryPort.findById("hh11")).thenReturn(USER_FROM_REPO);
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(0).getId())).thenReturn(SONGS_FROM_REPO.get(0));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(1).getId())).thenReturn(SONGS_FROM_REPO.get(1));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(2).getId())).thenReturn(SONGS_FROM_REPO.get(2));

        playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);

        Playlist playlist = playlistRespositoryPort.findById(id);

        assertEquals(3, playlist.getSongs().size());
    }
    @Test
    void tryToAddSongToThePlaylistThatAlreadyHasFiveSongs() throws PlaylistsNotFoundException, SongsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException, PlaylistSongLimitExceededException {
        String id = PLAYLISTS_FROM_REPO.get(2).getId();
        List<SongDTO> songs = new ArrayList<>();
        songs.add(SONGS_FROM_REPO.get(0).toDTO());
        songs.add(SONGS_FROM_REPO.get(1).toDTO());
        songs.add(SONGS_FROM_REPO.get(2).toDTO());

        List<Song> songs2 = new ArrayList<>();
        songs2.add(SONGS_FROM_REPO.get(0));
        songs2.add(SONGS_FROM_REPO.get(1));
        songs2.add(SONGS_FROM_REPO.get(2));
        songs2.add(SONGS_FROM_REPO.get(3));
        songs2.add(SONGS_FROM_REPO.get(4));
        Playlist playlist1 = new Playlist("hhrr", songs2);

        when(playlistRespositoryPort.findById(id)).thenReturn(playlist1);
        when(userRepositoryPort.findById(any())).thenReturn(USER_FROM_REPO_COMMON);
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(0).getId())).thenReturn(SONGS_FROM_REPO.get(0));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(1).getId())).thenReturn(SONGS_FROM_REPO.get(1));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(2).getId())).thenReturn(SONGS_FROM_REPO.get(2));

         Assertions.assertThrows(PlaylistSongLimitExceededException.class, () ->{
             playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);
        });
    }
    @Test
    void tryToAddSongToThePlaylistButExceedFiveSongs() throws PlaylistsNotFoundException, SongsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException, PlaylistSongLimitExceededException {
        String id = PLAYLISTS_FROM_REPO.get(2).getId();
        List<SongDTO> songs = new ArrayList<>();
        songs.add(SONGS_FROM_REPO.get(0).toDTO());
        songs.add(SONGS_FROM_REPO.get(1).toDTO());
        songs.add(SONGS_FROM_REPO.get(2).toDTO());

        List<Song> songs2 = new ArrayList<>();
        songs2.add(SONGS_FROM_REPO.get(0));
        songs2.add(SONGS_FROM_REPO.get(1));
        songs2.add(SONGS_FROM_REPO.get(2));
        Playlist playlist1 = new Playlist("hhrr", songs2);

        when(playlistRespositoryPort.findById(id)).thenReturn(playlist1);
        when(userRepositoryPort.findById(any())).thenReturn(USER_FROM_REPO_COMMON);
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(0).getId())).thenReturn(SONGS_FROM_REPO.get(0));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(1).getId())).thenReturn(SONGS_FROM_REPO.get(1));
        when(songRepositoryPort.findById(SONGS_FROM_REPO.get(2).getId())).thenReturn(SONGS_FROM_REPO.get(2));

        Assertions.assertThrows(PlaylistSongLimitExceededException.class, () ->{
            playlistServicePort.addSongsToPlaylist(id,USER_FROM_REPO.getId(),songs);
        });
    }


}
