package com.jon.service;

import com.jon.mapper.EmployeeMapper;
import com.jon.payroll.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public List<Employee> findAll() {
        return employeeMapper.selectList(null);
    }

    public Employee getById(Integer id) {
        return employeeMapper.selectById(id);
    }

    public Boolean insertOne(Employee employee) {
        return employeeMapper.insert(employee) == 1;
    }

    public Boolean updateEmployeeById(Employee employee) {
        return employeeMapper.updateById(employee) == 1;
    }

    public Boolean deleteEmployeeById(Integer id) {
        return employeeMapper.deleteById(id) == 1;
    }
}
