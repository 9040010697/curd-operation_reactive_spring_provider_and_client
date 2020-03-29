package com.reactive.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.model.Employee;
import com.reactive.util.RestClient;

import reactor.core.publisher.Mono;

@Component
public class ClientController {
	

	@Autowired
	private RestClient restClient;
	
	//POST
	public Mono<ServerResponse> saveEmp(ServerRequest serverRequest){
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(restClient.saveEmp(serverRequest.bodyToMono(Employee.class)), Employee.class);
		}
	
	//GET
	public Mono<ServerResponse> getAllEmployee(ServerRequest serverRequest) {
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(restClient.getAllEmp(), Employee.class);
	}
	
	//GET
	public Mono<ServerResponse> getEmployee(ServerRequest serverRequest) {
		String empId = serverRequest.pathVariable("id");
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(restClient.getEmpById(empId), Employee.class);
	}
	
	//PUT
	public Mono<ServerResponse> updateEmployee(ServerRequest serverRequest) {
		Mono<Employee> employee = serverRequest.bodyToMono(Employee.class);
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(restClient.updateEmp(employee), Employee.class);
	}
	
	//DELETE
	public Mono<ServerResponse> deleteEmployee(ServerRequest serverRequest) {
		String empId = serverRequest.pathVariable("id");
		return ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.build(restClient.deleteEmpById(empId));
	}

}
