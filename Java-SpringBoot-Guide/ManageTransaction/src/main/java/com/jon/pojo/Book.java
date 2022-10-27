package com.jon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Data
@TableName("books")
public class Book {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String type;
    private String name;
    private String description;

    public Book(String type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }
}
