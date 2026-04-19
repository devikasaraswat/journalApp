package net.project.journalApp.repository;


import net.project.journalApp.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryImplTests {


    @Autowired
    private UserRepositoryImpl userRepository;

    @Disabled
    @Test
    public void testSaveNewUser(){
       Assertions.assertNotNull(userRepository.getUsersForSA());
    }


}
