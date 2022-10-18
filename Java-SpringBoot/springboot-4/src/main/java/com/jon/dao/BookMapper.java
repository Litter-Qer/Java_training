package com.jon.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jon.domain.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
