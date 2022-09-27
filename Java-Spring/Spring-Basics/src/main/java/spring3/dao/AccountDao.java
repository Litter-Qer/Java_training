package spring3.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import spring3.domain.Account;

import java.util.List;

public interface AccountDao {
    @Insert("insert into tb_users(name, money) values(#{name},#{money})")
    void save(Account account);

    @Delete("delete from tb_users where id = #{id}")
    void update(Account account);

    @Update("update tb_users set name = #{name}, money = #{money} where id = #{id}")
    void delete(Integer id);

    @Select("select * from tb_users where id = #{id}")
    Account findById(Integer id);

    @Select("select * from tb_users")
    List<Account> findAll();
}
