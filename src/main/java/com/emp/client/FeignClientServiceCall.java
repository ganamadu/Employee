package com.emp.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.emp.core.Department;

@Component
@FeignClient(name="dept-service")
public interface FeignClientServiceCall {
	Logger logger = LoggerFactory.getLogger(FeignClientServiceCall.class);

	@GetMapping("/dept/list")
    public List<Department> getDeptList();
	
	@GetMapping("/dept/deptno/{id}")
	public Department getDeptByNo(@PathVariable Integer id);
}
