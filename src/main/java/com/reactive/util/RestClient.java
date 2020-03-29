package com.reactive.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.reactive.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RestClient {
	
	private static final String baseUrl= "http://localhost:8085/rest/employee";
	private WebClient webClient;

	public RestClient() {
		this.webClient = WebClient.create(baseUrl);
	}
	
	public Mono<Employee> saveEmp(Mono<Employee> bodyToMono) {
		return webClient.post()
		.uri("/save")
		.body(bodyToMono, Employee.class)
		.exchange()
		.flatMap(response-> response.bodyToMono(Employee.class));
	}

	public Flux<Employee> getAllEmp() {
		return webClient
			.get()
			.uri("/all")
			.exchange()
			.flatMapMany( res-> res.bodyToFlux(Employee.class));
	}

	public Mono<Employee> getEmpById(String empId) {
		return webClient.get()
		.uri("/get/{id}", empId)
		.exchange()
		.flatMap(response-> response.bodyToMono(Employee.class));
	}
	
	public Mono<Employee> updateEmp(Mono<Employee> bodyToMono) {
		return webClient.put()
		.uri("/update")
		.body(bodyToMono, Employee.class)
		.exchange()
		.flatMap(response-> response.bodyToMono(Employee.class));
	}
	
	public Mono<Void> deleteEmpById(String empId) {
		return webClient.delete()
		.uri("/delete/{id}", empId)
		.exchange()
		.then();
	}
	
}
