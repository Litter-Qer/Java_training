package com.jon.domian;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@TableName("books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @TableId
    private Integer id;
    private String type;
    private String name;
    private String description;
}
