package com.example.resourceserver.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    boolean reset();
}
