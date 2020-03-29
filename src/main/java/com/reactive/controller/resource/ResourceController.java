package com.reactive.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.model.Employee;
import com.reactive.repository.EmployeeService;
import com.reactive.util.CommonUtils;

import reactor.core.publisher.Mono;

@Component
public class ResourceController {

	@Autowired
	private EmployeeService employeeService;

	// POST
	public Mono<ServerResponse> save(ServerRequest serverRequest) {
		Mono<Employee> employee = serverRequest.bodyToMono(Employee.class);
	
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(employee.map(CommonUtils::generateEmpId)
						.flatMap(employeeService::save), Employee.class);
	}

	// GET
	public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
		return ServerResponse.ok().body(employeeService.findAll(), Employee.class);
	}

	// GET with PathVariable
	public Mono<ServerResponse> getId(ServerRequest serverRequest) {

		String empId = serverRequest.pathVariable("id");
		return ServerResponse.ok().body(employeeService.findById(empId), Employee.class);
	}
	
	//PUT
	public Mono<ServerResponse> update(ServerRequest serverRequest) {
		Mono<Employee> employee = serverRequest.bodyToMono(Employee.class);
		return ServerResponse.ok().body(employee.flatMap(employeeService::update), Employee.class);
	}
	
	//DELETE
	public Mono<ServerResponse> delete(ServerRequest serverRequest) {
		String empId = serverRequest.pathVariable("id");
		return ServerResponse.accepted().build(employeeService.delete(empId));
	}

}
