package com.yash.rest.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.rest.exception.EmployeeNotFoundException;
import com.yash.rest.models.Employee;
import com.yash.rest.repositiory.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getEmployees() {
		logger.info("EmployeeServiceImpl.getEmployees method execution started.");
		return employeeRepository.findAll();
	}

	public Employee createEmployee(Employee employee) {
		logger.info("EmployeeServiceImpl.createEmployee method execution started.");
		return employeeRepository.save(employee);
	}

	public Employee findEmployeeById(Long empId) {
		logger.info("EmployeeServiceImpl.findEmployeeById method execution started.");
		Optional<Employee> employee = employeeRepository.findById(empId);
		if (employee.isPresent()) {
			return employee.get();
		}
		return null;
	}

	public Employee updateEmployeeById(Long empId, Employee updatedEmployee) {
		logger.info("EmployeeServiceImpl.updateEmployeeById method execution started.");

		Optional<Employee> optionalEmp = employeeRepository.findById(empId);
		if (optionalEmp.isPresent()) {
			Employee employee = optionalEmp.get();
			employee.setAddress(updatedEmployee.getAddress());
			employee.setAge(updatedEmployee.getAge());
			employee.setContactNumber(updatedEmployee.getContactNumber());
			employee.setFirstName(updatedEmployee.getFirstName());
			employee.setLastName(updatedEmployee.getLastName());
			employee.setSalary(updatedEmployee.getSalary());
			return employeeRepository.save(employee);
		}
		return null;

	}

	public void deleteEmployeeById(Long empId) {
		logger.info("EmployeeServiceImpl.deleteEmployeeById method execution started.");

		Optional<Employee> optionalEmp = employeeRepository.findById(empId);
		if (optionalEmp.isPresent()) {
			employeeRepository.deleteById(empId);
		} else {
			throw new EmployeeNotFoundException("Employee does not exist.");
		}

	}

}
