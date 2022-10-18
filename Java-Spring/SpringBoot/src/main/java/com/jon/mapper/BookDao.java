package com.jon.mapper;


import com.jon.domain.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookDao {
    @Select("select * from tb_users where id = #{id}")
    public Book getById(Integer id);
}
