package com.example.resource.mapper;

import com.example.resource.pojo.ReadExcel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ListenerMapper {

    void insertRecruitUser(ReadExcel data);

    void insertVolunteer1(ReadExcel data);

    void insertVolunteer2(ReadExcel data);

    void insertStatus(ReadExcel data);
}
