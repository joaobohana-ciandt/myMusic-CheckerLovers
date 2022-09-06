package com.ciandt.summit.bootcamp2022.infra.adapters.entities;

import com.ciandt.summit.bootcamp2022.domains.userType.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TipoUsuario")
public class UserTypeEntity {
    @Id
    @Column(name = "Id")
    private String id = UUID.randomUUID().toString();

    @Column(name = "Descricao")
    private String description;

    public UserTypeEntity(UserType userType) {
        this.id = userType.getId();
        this.description = userType.getDescription();
    }

    public UserType toUserType(){
        return new UserType(this.id, this.description);
    }
}
