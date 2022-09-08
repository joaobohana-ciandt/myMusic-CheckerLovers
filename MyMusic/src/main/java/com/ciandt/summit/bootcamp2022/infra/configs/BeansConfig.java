package com.ciandt.summit.bootcamp2022.infra.configs;

import com.ciandt.summit.bootcamp2022.domains.playlists.adapters.services.PlaylistServiceImp;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.repositories.PlaylistRespositoryPort;
import com.ciandt.summit.bootcamp2022.domains.songs.adapters.services.SongServiceImp;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.interfaces.SongServicePort;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.repositories.SongRepositoryPort;
import com.ciandt.summit.bootcamp2022.domains.users.adapters.services.UserServiceImp;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class BeansConfig {
    @Bean
    public SongServicePort songService(SongRepositoryPort songRepositoryPort) {
        return new SongServiceImp(songRepositoryPort);
    }

    @Bean
    public UserServicePort userService(UserRepositoryPort userRepositoryPort){
        return new UserServiceImp(userRepositoryPort);
    }
    @Bean
    public PlaylistServicePort playlistService(PlaylistRespositoryPort playlistRespositoryPort, UserRepositoryPort userRepositoryPort, SongRepositoryPort songRepositoryPort){
        return new PlaylistServiceImp(playlistRespositoryPort, userRepositoryPort, songRepositoryPort);
    }

}
