package com.jon.domian;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("books")
public class Book {
    private Integer id;
    private String name;
    private String type;
    private String description;
}
