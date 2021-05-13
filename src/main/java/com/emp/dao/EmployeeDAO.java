package com.emp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emp.core.Employee;


@Repository
public interface EmployeeDAO extends CrudRepository<Employee, Long> {

    @Query("select e from Employee e")
    public List<Employee> getEmpList();

    @Query("select e from Employee e where e.empno=:empno")
    public Employee getEmpById(@Param("empno")Integer empno);
    
    @Query("select e from Employee e where e.deptno=:deptno")
    public List<Employee> getEmpListByDeptno(@Param("deptno")Integer deptno);

}
