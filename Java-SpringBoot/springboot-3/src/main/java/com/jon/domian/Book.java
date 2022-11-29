package com.jon.domian;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@TableName("books")
//@Table(name = "buckets")
public class Book {
    private Integer id;
    private String name;
    private String type;
    //    @Column(name = "CUME_DIST")
    private String description;
}
