package com.ciandt.summit.bootcamp2022.unit.controllers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.PlaylistController;
import com.ciandt.summit.bootcamp2022.application.adapters.controllers.handlers.AuthorizationInterceptor;
import com.ciandt.summit.bootcamp2022.domains.artists.Artist;
import com.ciandt.summit.bootcamp2022.domains.artists.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistSongsRequestDTO;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaylistServicePort playlistServicePort;

    @MockBean
    private AuthorizationInterceptor authorizationInterceptor;

    private Playlist playlist;
    private PlaylistSongsRequestDTO defaultPlaylistSongsRequestDTO;
    private MockHttpServletRequestBuilder mockHttpServletRequestBuilder;

    private final String USER = "user";
    private final String TOKEN = "token";
    private final String SONG_ID = "SONG_ID";
    private final String PLAYLIST_ID = "PLAYLIST_ID";
    private final String USER_ID = "USER_ID";

    @BeforeEach
    public void setupAuthorizer() throws Exception {
        Artist artist = new Artist("uuid", "artist name", new ArrayList<>());
        Song song1 = new Song("uuid", "song 1", artist);
        Song song2 = new Song("uuid", "song 2", artist);

        playlist = new Playlist("uuid", List.of(song1, song2));

        List<SongDTO> songsMappedToDTO = playlist.getSongs()
                .stream()
                .map(Song::toDTO)
                .collect(Collectors.toList());
        defaultPlaylistSongsRequestDTO = new PlaylistSongsRequestDTO(songsMappedToDTO);

        mockHttpServletRequestBuilder = post("/playlists/{playlistId}/musicas", PLAYLIST_ID)
                .header("token", TOKEN)
                .header("user", USER);
        mockHttpServletRequestBuilder = post("/api/playlists/{playlistId}/musicas", PLAYLIST_ID)
                .contentType(MediaType.APPLICATION_JSON);

        when(authorizationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(true);
    }

    @ParameterizedTest
    @MethodSource("songsRequestDTOGenerator")
    public void addSongsToPlaylistTest(PlaylistSongsRequestDTO playlistSongsRequestDTO) throws Exception {
        when(playlistServicePort.addSongsToPlaylist(PLAYLIST_ID, USER_ID,playlistSongsRequestDTO.getData()))
                .thenReturn(null);

        mockMvc.perform(mockHttpServletRequestBuilder.content(playlistSongsRequestDTO.toString()))
                .andExpect(status().isCreated());
    }

    @Test
    public void cannotFindPlaylistTest() throws Exception {
        PlaylistsNotFoundException exception = new PlaylistsNotFoundException("Specified playlist was not found");

        when(playlistServicePort.addSongsToPlaylist(PLAYLIST_ID,USER_ID,defaultPlaylistSongsRequestDTO.getData()))
                .thenThrow(exception);

        mockMvc.perform(mockHttpServletRequestBuilder.content(defaultPlaylistSongsRequestDTO.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cannotFindSongsTest() throws Exception {
        when(playlistServicePort.addSongsToPlaylist(PLAYLIST_ID, USER_ID,defaultPlaylistSongsRequestDTO.getData()))
                .thenThrow(new SongsNotFoundException("Specified song was not found."));

        mockMvc.perform(mockHttpServletRequestBuilder.content(defaultPlaylistSongsRequestDTO.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void cannotAddDuplicateSongsInPlaylist() throws Exception {
        when(playlistServicePort.addSongsToPlaylist(PLAYLIST_ID,USER_ID,defaultPlaylistSongsRequestDTO.getData()))
                .thenThrow(DuplicatedSongInPlaylist.class);

        mockMvc.perform(mockHttpServletRequestBuilder.content(defaultPlaylistSongsRequestDTO.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteMusicPlaylistTest() throws Exception {
        when(playlistServicePort.removeSongFromPlaylist(PLAYLIST_ID, SONG_ID)).thenReturn(null);

        mockMvc.perform(
                        delete("/playlists/{playlistId}/musicas/{musicaId}", PLAYLIST_ID, SONG_ID)
                                .header("token", TOKEN)
                                .header("user", USER)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNonExistentSongFromPlaylist() throws Exception {
        when(playlistServicePort.removeSongFromPlaylist(PLAYLIST_ID, SONG_ID))
                .thenThrow(new SongsNotFoundException("Specified song was not found."));

        mockMvc.perform(
                        delete("/playlists/{playlistId}/musicas/{musicaId}", PLAYLIST_ID, SONG_ID)
                                .header("token", TOKEN)
                                .header("user", USER)).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteSongFromNonExistentPlaylist() throws Exception {
        when(playlistServicePort.removeSongFromPlaylist(PLAYLIST_ID, SONG_ID))
                .thenThrow(new PlaylistsNotFoundException("Specified playlist was not found."));

        mockMvc.perform(
                delete("/playlists/{playlistId}/musicas/{musicaId}", PLAYLIST_ID, SONG_ID)
                        .header("token", TOKEN)
                        .header("user", USER))
                .andExpect(status().isBadRequest());
    }

    private static List<PlaylistSongsRequestDTO> songsRequestDTOGenerator() {
        ArtistDTO artistDTO = new ArtistDTO("uuid", "artist name");
        SongDTO songDTO1 = new SongDTO("uuid", "song 1", artistDTO);
        SongDTO songDTO2 = new SongDTO("uuid", "song 2", artistDTO);

        return List.of(new PlaylistSongsRequestDTO(List.of()), new PlaylistSongsRequestDTO(List.of(songDTO1, songDTO2)));
    }
}
