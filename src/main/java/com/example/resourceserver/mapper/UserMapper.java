package com.example.resourceserver.mapper;

import com.example.resourceserver.pojo.Users;
import com.example.resourceserver.pojo.Volunteer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
   List<Users> getAllInfo();


   List<Volunteer> getVolunteer(String id);
}
