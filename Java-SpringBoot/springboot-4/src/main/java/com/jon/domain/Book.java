package com.jon.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "books")
public class Book {
    private Integer id;
    private String name;
    private String type;
    private String description;
}
