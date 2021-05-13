package com.emp.core;

import java.util.List;

public class CommonEntity {
	
	List<Employee> employeeList;
	List<Address> addressList;
	Student student;
	Department department;
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public List<Address> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

}
