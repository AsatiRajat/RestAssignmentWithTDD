package com.yash.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yash.rest.models.Employee;

@Service
public interface EmployeeService {

	public List<Employee> getEmployees();

	public Employee createEmployee(Employee employee);

	public Employee findEmployeeById(Long empId);

	public Employee updateEmployeeById(Long empId, Employee updatedEmployee);

	public void deleteEmployeeById(Long empId);

}
