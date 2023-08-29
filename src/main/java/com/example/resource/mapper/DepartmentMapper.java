package com.example.resource.mapper;

import com.example.resource.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    boolean nextDepartment(String id);
     List<Users> choosePassPerson(String departmentId);
    List<Users> chooseNotPassPerson(String departmentId);
    List<Users> chooseWillPassPerson(String departmentId);

    boolean changeToPassStatus(String id);
    boolean changeToNoPassStatus(String id);
    boolean departmentExist(String departmentId);
    boolean userExist(String id);

    boolean selectMessageId(String messageId);
}
