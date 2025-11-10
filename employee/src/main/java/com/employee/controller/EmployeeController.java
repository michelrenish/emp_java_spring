package com.employee.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping({"/", "/create", "/add"})
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        Employee saved = service.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /employee/{id}
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // GET /employee/all
    @GetMapping("/all")
    public List<Employee> getAll() {
        return service.getAll();
    }

    // DELETE /employee/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /employee/update-salary?id=1&salary=12345.67
    @PutMapping("/update-salary")
    public Employee updateSalary(@RequestParam("id") Long id,
                                 @RequestParam("salary") Double salary) {
        return service.updateSalary(id, salary);
    }

    // GET /employee/average-salary
    @GetMapping("/average-salary")
    public Map<String, Double> averageSalaryByDepartment() {
        return service.averageSalaryByDepartment();
    }

    // GET /employee/highest-salary
    @GetMapping("/highest-salary")
    public ResponseEntity<Employee> highestSalary() {
        return service.highestPaid()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // GET /employee/above-average
    @GetMapping("/above-average")
    public List<Employee> aboveAverage() {
        return service.aboveOverallAverage();
    }
}