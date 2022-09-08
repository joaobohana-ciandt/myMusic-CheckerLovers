package com.ciandt.summit.bootcamp2022.infra.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepository implements UserRepositoryPort {

    @Autowired
    private SpringUserRepository userRepository;
    @Override
    public User findById(String id) throws UserNotFoundException {
        Optional<UserEntity> userEntity = this.userRepository.findById(id);
        if(userEntity.isPresent()){
            return userEntity.get().toUser();
        }else {
            throw new UserNotFoundException("Specified user was not found");
        }
    }
}
