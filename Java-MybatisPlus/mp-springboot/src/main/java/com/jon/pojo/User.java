package com.jon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends Model<User> {
    @TableId
    private long id;
    private String userName;

    @TableField(select = true)
    private String password;
    private String name;
    private String age;

    @TableField(value = "email")
    private String userEmail;

    @TableField(exist = false)
    private String address; // 数据库中没有这个字段
}
