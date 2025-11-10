package com.employee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@SpringBootApplication
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
	}
    @Bean
    CommandLineRunner seed(EmployeeService service) {
        return args -> {
            if (service.getAll().isEmpty()) {
                service.create(new Employee(101L, "Asha", 90000.0, "Engineering"));
                service.create(new Employee(102L, "Rahul", 60000.0, "Engineering"));
                service.create(new Employee(103L, "Neha", 70000.0, "HR"));
                service.create(new Employee(104L, "Karan", 120000.0, "Finance"));
            }
        };
    }
}
