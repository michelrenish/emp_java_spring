package com.employee.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employee.exception.NotFoundException;
import com.employee.model.Employee;

@Service
public class EmployeeService {

	private final Map<Long, Employee> store = new ConcurrentHashMap<>();

	public Employee create(Employee e) {
		if (e.getId() == null) {
			throw new IllegalArgumentException("Employee ID must be provided");
		}
		if (store.containsKey(e.getId())) {
			throw new IllegalArgumentException("Employee with ID " + e.getId() + " already exists");
		}
		store.put(e.getId(), e);
		return e;
	}

	public Employee getById(Long id) {
		Employee e = store.get(id);
		if (e == null)
			throw new NotFoundException("Employee not found with id=" + id);
		return e;
	}

	public List<Employee> getAll() {
		return store.values().stream().sorted(Comparator.comparing(Employee::getId)).collect(Collectors.toList());
	}

	public void delete(Long id) {
		if (store.remove(id) == null) {
			throw new NotFoundException("Employee not found with id=" + id);
		}
	}

	public Employee updateSalary(Long id, double newSalary) {
		if (newSalary < 0)
			throw new IllegalArgumentException("salary must be non-negative");
		Employee e = getById(id);
		e.setSalary(newSalary);
		store.put(id, e);
		return e;
	}

 	public Map<String, Double> averageSalaryByDepartment() {
		return store.values().stream().collect(
				Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
	}

	public Optional<Employee> highestPaid() {
		return store.values().stream().max(Comparator.comparingDouble(Employee::getSalary));
	}

	public List<Employee> aboveOverallAverage() {
		Collection<Employee> values = store.values();
		if (values.isEmpty())
			return List.of();
		double avg = values.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);
		return values.stream().filter(e -> e.getSalary() > avg)
				.sorted(Comparator.comparingDouble(Employee::getSalary).reversed()).collect(Collectors.toList());
	}
}
