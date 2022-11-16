package com.jon.order.mapper;

import com.jon.order.pojo.Order;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {

    @Select("select * from cloud_order where id = #{id}")
    Order findById(Long id);
}
