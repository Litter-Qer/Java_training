package com.jon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jon.domian.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookDao extends BaseMapper<Book> {
}
