package com.jon.controller;

import com.jon.payroll.Employee;
import com.jon.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable Integer id) {
        return employeeService.getById(id);
    }

    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        return (employeeService.insertOne(employee)) ? employee : null;
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/{id}")
    public Boolean update(@RequestBody Employee employee, @PathVariable Integer id) {
        employee.setId(id);
        return employeeService.updateEmployeeById(employee);
    }

}
