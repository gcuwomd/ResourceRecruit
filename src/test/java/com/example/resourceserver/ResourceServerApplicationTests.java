package com.example.resourceserver;

import com.example.resourceserver.Service.Department;
import com.example.resourceserver.Service.User;
import com.example.resourceserver.util.OssUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
class ResourceServerApplicationTests {
@Autowired
User userService;
@Autowired
Department deaprtment;
@Autowired
    OssUtil ossUtil;
    @Test
    void contextLoads() {
    }
    @Test
    void getallinfo(){
        deaprtment.chooseNoPassPerson("1").toString();
    }
    @Test
    void stream() throws IOException {
        File f=new File("a.txt");
        ;
        System.out.println(ossUtil.put( f,"1"));
    }

}
