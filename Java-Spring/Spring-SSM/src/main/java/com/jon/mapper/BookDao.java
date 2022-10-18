package com.jon.mapper;

import com.jon.domain.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BookDao {
    @Insert("insert into tb_users values(null,#{type},#{name},#{description})")
    void save(Book book);

    @Update("update tb_users set type=#{type}, name=#{name}, description=#{description} where id = #{id}")
    void update(Book book);

    @Delete("delete from tb_users where id = #{id}")
    void delete(Integer id);

    @Select("select * from tb_users where id = #{id}")
    Book getById(Integer id);

    @Select("select * from tb_users")
    List<Book> getAll();
}
