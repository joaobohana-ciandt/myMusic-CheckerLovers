package com.ciandt.summit.bootcamp2022.unit.repositories;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.repositories.PlaylistRespositoryPort;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.ArtistEntity;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.PlaylistEntity;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.SongEntity;
import com.ciandt.summit.bootcamp2022.infra.adapters.repositories.SpringPlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class PlaylistRespositoryTest {
    @MockBean
    private SpringPlaylistRepository springPlaylistRepository;

    @Autowired
    private PlaylistRespositoryPort playlistRespositoryPort;

    private SongEntity song;
    private Playlist playlist;
    private PlaylistEntity playlistEntity;

    private final String ID = "jhjh1222";

    @BeforeEach
    public void setup() {
        ArtistEntity artistEntity = new ArtistEntity();
        artistEntity.setName("Antonio");

        playlist = new Playlist();
        playlistEntity = new PlaylistEntity();

        song = new SongEntity();
        song.setName("felicidade");
        song.setArtist(artistEntity);

        playlistEntity.setSongs(List.of(song));

        playlist = playlistEntity.toPlaylist();
    }

    @Test
    public void playlistFound() throws PlaylistsNotFoundException {
        when(springPlaylistRepository.findById(ID))
                .thenReturn(Optional.of(playlistEntity));

        Playlist result = playlistRespositoryPort.findById(ID);

        assertEquals(result, playlist);
    }

    @Test
    public void playlistNotFound() {
        String expectedExceptionMessage = "Specified playlist was not found";

        when(springPlaylistRepository.findById(ID))
                .thenReturn(Optional.empty());

        PlaylistsNotFoundException exception = assertThrows(PlaylistsNotFoundException.class, () -> {
            playlistRespositoryPort.findById(ID);
        });

        assertEquals(exception.getMessage(), expectedExceptionMessage);
    }

    @Test
    public void savePlaylist() {
        when(springPlaylistRepository.save(playlistEntity))
                .thenReturn(playlistEntity);

        Playlist result = playlistRespositoryPort.addSong(playlistEntity);

        assertEquals(result, playlist);
    }
}
