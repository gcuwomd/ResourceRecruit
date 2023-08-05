package com.example.resourceserver;

import com.example.resourceserver.Service.User;
import com.example.resourceserver.Service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceServerApplicationTests {
@Autowired
User userService;
    @Test
    void contextLoads() {
    }
    @Test
    void getallinfo(){
        userService.allInfo();
    }

}
