package com.emp.service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emp.core.Address;
import com.emp.core.Department;
import com.emp.core.Student;

@Service
public class CommonEntityService {
	
	 @Async
	 public CompletableFuture<Department> getDepartments(Integer deptno) {
		 String url = "http://localhost:8762/dept/deptno/"+deptno;
		 Department departmentResponse = getObjectApi(url, HttpMethod.GET, Department.class);
		 System.out.println("departmentResponse: "+departmentResponse.getDname());
		 return CompletableFuture.completedFuture(departmentResponse);
	 }
	 
	 @Async
	 public CompletableFuture<Student> getStudens(Integer sno) {
		 String url = "http://localhost:8081/stud/sno/"+sno;
		 Student studentResponse = getObjectApi(url, HttpMethod.GET, Student.class);
		 System.out.println("studentResponse: "+studentResponse.getSname());
		 return CompletableFuture.completedFuture(studentResponse);
	 }
	
	 @Async
	 public CompletableFuture<List<Address>> getAddress(Integer sno) {
		 String url = "http://localhost:8081/addr/studentNo/"+sno;
		 List<Address>  addressResponse = getListApi(url, HttpMethod.GET);
		 System.out.println("addressResponse: "+addressResponse.size());
		 return CompletableFuture.completedFuture(addressResponse);
 	 }
	 
	 public <T> List<T> getListApi(final String path, final HttpMethod method) {     
		    final RestTemplate restTemplate = new RestTemplate();
		    final ResponseEntity<List<T>> response = restTemplate.exchange(
		      path,
		      method,
		      null,
		      new ParameterizedTypeReference<List<T>>(){});
		    List<T> list = response.getBody();
		    return list;
		}
		
		public <T> T getObjectApi(final String path, final HttpMethod method, Class<T> returnType) {     
		    final RestTemplate restTemplate = new RestTemplate();
		    final ResponseEntity<T> response = restTemplate.exchange(
		      path,
		      method,
		      null,
		      returnType);
		    return response.getBody();
		}

}
