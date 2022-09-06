package com.ciandt.summit.bootcamp2022.unit;

import com.ciandt.summit.bootcamp2022.SummitBootcampApplication;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.UserEntity;
import com.ciandt.summit.bootcamp2022.infra.adapters.repositories.SpringUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SummitBootcampApplication.class)
public class UserRepositoryTest {
    @MockBean
    private SpringUserRepository springUserRepository;

    @Autowired
    private UserRepositoryPort userRepositoryPort;

    @BeforeEach
    public void setup(){
    }

}
