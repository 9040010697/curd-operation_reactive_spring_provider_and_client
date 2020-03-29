package com.reactive.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repo;

	public Mono<Employee> save(Employee employee) {
		return repo.save(employee);
	}

	public Flux<Employee> findAll() {
		return repo.findAll();
	}

	public Mono<Employee> findById(String id) {
		return repo.findById(id);
	}

	public Mono<Employee> update(Employee emp) {
		Mono<Employee> existingEmp = repo.findById(emp.getId());
		return existingEmp.flatMap(e-> repo.save(emp));
	}

	public Mono<Void> delete(String id) {
		return repo.deleteById(id);
	}

}
