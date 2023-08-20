package com.example.resource;

import com.example.resource.Service.Department;
import com.example.resource.Service.User;
import com.example.resource.util.MeaasgeUtil;
import com.example.resource.util.OssUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResourceServerApplicationTests {
@Autowired
User userService;
@Autowired
Department deaprtment;
@Autowired
    OssUtil ossUtil;
@Autowired
    MeaasgeUtil meaasgeUtil;
    @Test
    void contextLoads() {
    }
    @Test
    void getallinfo(){
        deaprtment.chooseNoPassPerson("1").toString();
    }
    @Test
    void stream() throws Exception {

    }

}
