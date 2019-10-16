package com.yash.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.rest.exception.EmployeeNotFoundException;
import com.yash.rest.models.Employee;
import com.yash.rest.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/employees")
@Api(value="EmployeeController", description="Employee's Rest API's")
public class EmployeeController {

	private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	public EmployeeController() {
	}

	@ApiOperation(value = "Get list of employees", response = Iterable.class, tags = "getEmployees")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "Not Authorized"),
            @ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(produces = { "application/json", "application/xml" })
	public List<Employee> getEmployees() {
		logger.info("EmployeeController.getEmployees method execution started.");
		return employeeService.getEmployees();
	}

	@ApiOperation(value = "add employee", response = Employee.class, tags = "addEmployee")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found") })
	@PostMapping(consumes = { "application/json", "application/xml" })
	public Employee addEmployee(@Valid @RequestBody Employee employee) throws Exception {
		logger.info("EmployeeController.addEmployee method execution started.");
		Employee newEmployee = employeeService.createEmployee(employee);
		if (newEmployee == null) {
			throw new EmployeeNotFoundException("Employee does not exist.");
		}
		return newEmployee;
	}

	@ApiOperation(value = "Get employee by empId", response = Employee.class, tags = "FindEmployee")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/{empId}", produces = { "application/json", "application/xml" })
	public Employee getEmployee(@PathVariable("empId") Long empId) {
		logger.info("EmployeeController.getEmployee method execution started.");
		Employee employee = employeeService.findEmployeeById(empId);
		if (employee == null) {
			throw new EmployeeNotFoundException("Employee does not exist.");
		}
		return employee;
	}

	@ApiOperation(value = "Upadte employee by empId", response = Employee.class, tags = "updateEmployee")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(value = "/{empId}", produces = { "application/json", "application/xml" })
	public Employee updateEmployee(@PathVariable("empId") Long empId, @Valid @RequestBody Employee updatedEmployee) {
		logger.info("EmployeeController.updateEmployee method execution started.");
		Employee employee = employeeService.updateEmployeeById(empId, updatedEmployee);
		if (employee == null) {
			throw new EmployeeNotFoundException("Employee does not exist.");
		}
		return employee;
	}

	@ApiOperation(value = "Delete employee by empId", response = Employee.class, tags = "deleteEmployee")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "No Content"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found") })
	@DeleteMapping(value = "/{empId}")
	public HttpStatus deleteEmployee(@PathVariable("empId") Long empId) {

		logger.info("EmployeeController.deleteEmployee method execution started.");
		employeeService.deleteEmployeeById(empId);
		return HttpStatus.FORBIDDEN;

	}

}
