package com.jon.user.mapper;

import com.jon.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    
    @Select("select * from cloud_user where id = #{id}")
    User findById(@Param("id") Long id);
}