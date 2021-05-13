package com.emp.client;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emp.core.Department;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@Service
public class DeptServiceCircuitBreakerClient {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${dept.service.endpoint}")
    private String deptService;
	
	@Retry(name = "deptService")
	@CircuitBreaker(name = "deptService", fallbackMethod = "getDefault")
	public Department getDepartmentById(Integer deptno) {
		System.out.println("Dept Service Endpoint: "+deptService);
		return this.restTemplate.getForEntity(this.deptService+"/circuit/deptno/"+deptno, Department.class).getBody();
	}
	
	@TimeLimiter(name = "deptService")
	@CircuitBreaker(name = "deptService", fallbackMethod = "getComplDefault")
	public CompletionStage<Department> getDeptById(Integer deptno) {
		System.out.println("Timer Method Called...");
		Supplier<Department> supplier = () -> 
		this.restTemplate.getForEntity(this.deptService+"/circuittimer/deptno/"+deptno, Department.class).getBody();
		return CompletableFuture.supplyAsync(supplier);
	}
	
	public Department getDefault(Integer deptno, Throwable throwable) {
		return new Department();
	}
	
	public CompletionStage<Department> getComplDefault(Integer deptno, Throwable throwable) {
		return CompletableFuture.supplyAsync(()->new Department());
	}

}

