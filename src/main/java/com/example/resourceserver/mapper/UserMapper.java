package com.example.resourceserver.mapper;

import com.example.resourceserver.pojo.Image;
import com.example.resourceserver.pojo.Statistics;
import com.example.resourceserver.pojo.Users;
import com.example.resourceserver.pojo.Volunteer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
   List<Users> getAllInfo(String organizationId);
   List<Volunteer> getVolunteer(String id);
    List<Statistics>statistics(String organizationId);
    Integer totalNumber(String organizationId);
    boolean addImage(String id, String url);
     List<Image> getImageById(String id);
    boolean organizationExist(String organizationId);
    List<Users> getInfoByDepartment(String departmentId);
    List<Users> getInfoByKey(String organizationId, String key);
    boolean insertStatus(String id, String departmentId);
    boolean insertUsers(Users users);
   boolean insertVolunteerList(String id, String departmentId, Integer level);

}
