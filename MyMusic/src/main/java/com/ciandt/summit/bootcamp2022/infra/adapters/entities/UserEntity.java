package com.ciandt.summit.bootcamp2022.infra.adapters.entities;

import com.ciandt.summit.bootcamp2022.domains.users.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuarios")
public class UserEntity {
    @Id
    @Column(name = "Id")
    private String id = UUID.randomUUID().toString();

    @Column(name = "Nome")
    private String name;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PlaylistId")
    private PlaylistEntity playlistEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TipoUsuarioId")
    private UserTypeEntity userTypeEntity;

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.playlistEntity = new PlaylistEntity(user.getPlaylist());
        this.userTypeEntity = new UserTypeEntity(user.getUserType());
    }

    public User toUser() {
        return new User(id, name, playlistEntity.toPlaylist(), userTypeEntity.toUserType());
    };
}
