package com.jon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jon.payroll.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
