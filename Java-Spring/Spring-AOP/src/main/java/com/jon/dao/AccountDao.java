package com.jon.dao;

import com.jon.domain.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountDao {
    @Update("update tb_users set money = money + #{money} where name = #{name}")
    void inMoney(@Param("name") String name, @Param("money") Double money);

    @Update("update tb_users set money = money - #{money} where name = #{name}")
    void outMoney(@Param("name") String name, @Param("money") Double money);

    @Select("select * from tb_users where id = #{id}")
    Account findById(@Param("id") Integer id);
}
