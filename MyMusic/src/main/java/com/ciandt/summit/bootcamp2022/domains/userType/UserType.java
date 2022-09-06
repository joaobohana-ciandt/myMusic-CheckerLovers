package com.ciandt.summit.bootcamp2022.domains.userType;

import com.ciandt.summit.bootcamp2022.domains.userType.dto.UserTypeDTO;

import java.util.Objects;

public class UserType {
    private String id;
    private String description;

    public UserType() {
    }

    public UserType (String id, String description){
        this.id = id;
        this.description = description;
    }

    public UserType(UserTypeDTO userTypeDTO){
        this.id = userTypeDTO.getId();
        this.description = userTypeDTO.getDescription();
    }

    public UserTypeDTO toDTO(){
        return new UserTypeDTO(this.id, this.description);
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserType userType = (UserType) o;
        return Objects.equals(id, userType.id) && Objects.equals(description, userType.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
