package com.ciandt.summit.bootcamp2022.domains.userType.dto;

import com.ciandt.summit.bootcamp2022.domains.userType.UserType;

import java.util.Objects;

public class UserTypeDTO {
    private String id;
    private String description;

    public UserTypeDTO (String id, String description){
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserType toUserType(){
        return new UserType(id, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTypeDTO that = (UserTypeDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id + '\"' +
                ",\"description\":\"" + description + '\"' +
                '}';
    }
}
