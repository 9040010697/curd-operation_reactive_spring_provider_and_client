package com.reactive;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.controller.client.ClientController;
import com.reactive.controller.resource.ResourceController;

@Configuration
public class ReactiveRouterConfig {

	@Bean
	public RouterFunction<ServerResponse> routerFunction(ResourceController routerHandlers, ClientController clientService) {
		return RouterFunctions.route(RequestPredicates.GET("/rest/employee/all"), routerHandlers::getAll)
				.andRoute(RequestPredicates.POST("/rest/employee/save").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), routerHandlers::save)
				.andRoute(RequestPredicates.PUT("/rest/employee/update"), routerHandlers::update)
				.andRoute(RequestPredicates.DELETE("/rest/employee/delete/{id}"), routerHandlers::delete)
				.andRoute(RequestPredicates.GET("/rest/employee/get/{id}"), routerHandlers::getId)
				
				//Client Configurations
				.andRoute(RequestPredicates.GET("/rest/employee/client/all"), clientService::getAllEmployee)
				.andRoute(RequestPredicates.POST("/rest/employee/client/save").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)), clientService::saveEmp)
				.andRoute(RequestPredicates.PUT("/rest/employee/client/update"), clientService::updateEmployee)
				.andRoute(RequestPredicates.DELETE("/rest/employee/client/delete/{id}"), clientService::deleteEmployee)
				.andRoute(RequestPredicates.GET("/rest/employee/client/get/{id}"), clientService::getEmployee);

	}
}
