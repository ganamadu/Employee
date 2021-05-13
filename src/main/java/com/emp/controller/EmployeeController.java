package com.emp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.client.DeptServiceCircuitBreakerClient;
import com.emp.client.FeignClientServiceCall;
import com.emp.core.Address;
import com.emp.core.CommonEntity;
import com.emp.core.Department;
import com.emp.core.Employee;
import com.emp.core.Student;
import com.emp.service.CommonEntityService;
import com.emp.service.EmployeeService;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired 
	FeignClientServiceCall feignClientServiceCall;

	@Autowired 
	DeptServiceCircuitBreakerClient deptServiceCircuitBreakerClient;
	
	@Autowired
	CommonEntityService commonEntityService;


	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}


	@GetMapping("/list") public List<Employee> employeeList() {
		System.out.println("Employee Controller...."); 
		return employeeService.getEmpList(); }

	@GetMapping("/empno/{id}") public Employee getEmpById(@PathVariable Integer id) { 
		System.out.println("Employee Controller...."); 
		return	employeeService.getEmpById(id); }


	@GetMapping("/deptlist") 
	public List<Department> getDeptList() {
		logger.info("Inside Employee Control call...."); 
		return feignClientServiceCall.getDeptList(); 
	}
	
	@GetMapping("/deptno/{deptno}") 
	public Department getDeptByNo(@PathVariable Integer deptno) {
		logger.info("Inside Employee Control call...."); 
		return feignClientServiceCall.getDeptByNo(deptno); 
	}

	@GetMapping("/ciruitdeptno/{deptno}") 
	public Department getCircuitDeptNo(@PathVariable Integer deptno) {
		System.out.println("RestTemplate Service call....");
		return deptServiceCircuitBreakerClient.getDepartmentById(deptno); 
	}
	
	@GetMapping("/timerdeptno/{deptno}") 
	public CompletionStage<Department> getTimerLimitByDeptNo(@PathVariable Integer deptno) {
		System.out.println("RestTemplate timer method call....");
		return deptServiceCircuitBreakerClient.getDeptById(deptno);
	}
	
	@GetMapping("/commonentity/{deptno}")
	public CommonEntity getCommonEntity(@PathVariable Integer deptno) throws InterruptedException, ExecutionException {
		CommonEntity commonEntity = new CommonEntity();
		List<Employee> empList = employeeService.getEmpByDeptnoList(deptno);
		System.out.println("EmpList Response: "+empList.size());
		if(deptno == 20) {
			CompletableFuture<Department> deptFuture = commonEntityService.getDepartments(deptno);
			System.out.println("deptFuture Response: "+deptFuture.get().getDname()+"  DeptNO: "+deptno);
			commonEntity.setDepartment(deptFuture.get());
			CompletableFuture<Student>  studentFuture = commonEntityService.getStudens(8);
			System.out.println("studentFuture Response: "+studentFuture.get().getSname()+"  Sno: "+deptno);
			commonEntity.setStudent(studentFuture.get());
			CompletableFuture<List<Address>> addressFuture = commonEntityService.getAddress(8);
			System.out.println("addressFuture Response: "+addressFuture.get().size()+"  Sno: "+deptno);
			commonEntity.setAddressList(addressFuture.get());
		} else if(deptno == 30) {
			CompletableFuture<Department> deptFuture = commonEntityService.getDepartments(deptno);
			System.out.println("deptFuture Response: "+deptFuture.get().getDname()+"  DeptNO: "+deptno);
			commonEntity.setDepartment(deptFuture.get());
			CompletableFuture<Student>  studentFuture = commonEntityService.getStudens(7);
			System.out.println("studentFuture Response: "+studentFuture.get().getSname()+"  Sno: "+deptno);
			commonEntity.setStudent(studentFuture.get());
			CompletableFuture<List<Address>> addressFuture = commonEntityService.getAddress(7);
			System.out.println("addressFuture Response: "+addressFuture.get().size()+"  Sno: "+deptno);
			commonEntity.setAddressList(addressFuture.get());
		} else if(deptno == 10) {
			CompletableFuture<Department> deptFuture = commonEntityService.getDepartments(deptno);
			System.out.println("deptFuture Response: "+deptFuture.get().getDname()+"  DeptNO: "+deptno);
			commonEntity.setDepartment(deptFuture.get());
			CompletableFuture<Student>  studentFuture = commonEntityService.getStudens(deptno);
			System.out.println("studentFuture Response: "+studentFuture.get().getSname()+"  Sno: "+deptno);
			commonEntity.setStudent(studentFuture.get());
			CompletableFuture<List<Address>> addressFuture = commonEntityService.getAddress(deptno);
			System.out.println("addressFuture Response: "+addressFuture.get().size()+"  Sno: "+deptno);
			commonEntity.setAddressList(addressFuture.get());
		}
		commonEntity.setEmployeeList(empList);
		return commonEntity;
	}
	
	


}
