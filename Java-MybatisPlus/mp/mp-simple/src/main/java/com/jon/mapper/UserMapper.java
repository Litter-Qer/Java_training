package com.jon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jon.pojo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> findAll();
}
