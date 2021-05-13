package com.emp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emp.core.Department;
import com.emp.core.Employee;
import com.emp.dao.EmployeeDAO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;



	public List<Employee> getEmpList() {
		return employeeDAO.getEmpList();
	}

	public Employee getEmpById(Integer empno) {
		return employeeDAO.getEmpById(empno);
	}

	public List<Employee> getEmpByDeptnoList(Integer deptno) {
		return employeeDAO.getEmpListByDeptno(deptno);
	}

	@CircuitBreaker(name = "deptService", fallbackMethod = "getDefaultList")
	public List<Department> getDeptList() {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("Called Circuit Breacker Mechanisim...");
		ResponseEntity<Department[]> responseEntity = restTemplate.getForEntity("http://localhost:8085/dept/list", Department[].class);
		return Arrays.asList(responseEntity.getBody());
	}

	public List<Department> getDefaultList() {
		return new ArrayList<>();
	}




}
