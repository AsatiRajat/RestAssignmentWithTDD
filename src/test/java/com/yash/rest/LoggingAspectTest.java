package com.yash.rest;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.context.junit4.SpringRunner;

import com.yash.rest.config.LoggingAspect;
import com.yash.rest.exception.EmployeeNotFoundException;
import com.yash.rest.models.Employee;
import com.yash.rest.repositiory.EmployeeRepository;
import com.yash.rest.service.EmployeeService;
import com.yash.rest.service.EmployeeServiceImpl;

@RunWith(SpringRunner.class)
public class LoggingAspectTest {

	@Mock
	EmployeeRepository employeeRepository;

	@InjectMocks
	EmployeeServiceImpl employeeService;

	@Test
	public void AOPTest() {
		Employee employee = new Employee(100L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0);

		when(employeeRepository.findById(100L)).thenReturn(Optional.of(employee));

		AspectJProxyFactory proxyFactory = new AspectJProxyFactory(employeeService);
		proxyFactory.addAspect(LoggingAspect.class);
		EmployeeService proxy = proxyFactory.getProxy();
		proxy.findEmployeeById(employee.getEmpId());
	}

	// @Test(expected = EmployeeNotFoundException.class)
	public void AOPExceptionTest() {
		Employee employee = new Employee(1L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0);
		when(employeeRepository.findById(1000L)).thenThrow(EmployeeNotFoundException.class);

		AspectJProxyFactory proxyFactory = new AspectJProxyFactory(employeeService);
		proxyFactory.addAspect(LoggingAspect.class);
		EmployeeService proxy = proxyFactory.getProxy();
		proxy.findEmployeeById(employee.getEmpId());
	}

	@Test
	public void pointcutTest() {
		LoggingAspect loggingAspect = new LoggingAspect();
		loggingAspect.method1();
		loggingAspect.method2();
	}

}
