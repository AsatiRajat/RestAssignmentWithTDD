package com.yash.rest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.yash.rest.exception.EmployeeNotFoundException;
import com.yash.rest.models.Employee;
import com.yash.rest.repositiory.EmployeeRepository;
import com.yash.rest.service.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeServiceImpl employeeServiceImpl;

	@Mock
	private EmployeeRepository employeeRepository;

	@Test
	public void shouldReturnAllEmployeesTest() {
		List<Employee> mockEmployees = mockEmployeesData();
		when(employeeRepository.findAll()).thenReturn(mockEmployees);
		employeeServiceImpl.getEmployees();
		verify(employeeRepository).findAll();
	}

	@Test
	public void shouldReturnAddedEmployeeTest() {
		Employee newEmployee = new Employee(36L, "magapatta", 24, "9425159443", "Rajat", "Asati", 11000.0);
		Employee mockEmployee = new Employee(36L, "magapatta", 24, "9425159443", "Rajat", "Asati", 11100.0);

		when(employeeRepository.save(newEmployee)).thenReturn(mockEmployee);
		employeeServiceImpl.createEmployee(newEmployee);
		verify(employeeRepository).save(newEmployee);
	}

	@Test
	public void shouldReturnEmployeeByEmpId() {
		Employee mockEmployee = this.mockEmployeeData();
		Optional<Employee> empOptional = Optional.of(mockEmployee);

		when(employeeRepository.findById(1L)).thenReturn(empOptional);
		employeeServiceImpl.findEmployeeById(1L);
		verify(employeeRepository, Mockito.atLeast(1)).findById(1L);
	}

	@Test
	public void shouldReturnNullIfEmployeeNotFoundByEmpId() {
		when(employeeRepository.findById(100L)).thenReturn(Optional.empty());
		employeeServiceImpl.findEmployeeById(100L);
		verify(employeeRepository).findById(100L);
	}

	@Test
	public void shouldReturnUpdatedEmployeeByEmpId() {
		Employee updatedEmployee = new Employee(4L, "updated address", 20, "9425159443", "Asati", "Rajat", 1000.0);
		Employee mockEmployee = new Employee(4L, "4th address", 26, "1234567890", "Rajat", "Asati", 11100.0);

		Optional<Employee> empOptional = Optional.of(mockEmployee);
		when(employeeRepository.findById(4L)).thenReturn(empOptional);
		when(employeeRepository.save(updatedEmployee)).thenReturn(mockEmployee);
		employeeServiceImpl.updateEmployeeById(4L, updatedEmployee);
		verify(employeeRepository).save(updatedEmployee);
	}

	@Test
	public void shouldReturnNullIfEmployeeNotFoundByEmpIdForUpdateEmployee() {
		Employee updatedEmployee = new Employee(4L, "updated address", 20, "9425159443", "Asati", "Rajat", 1000.0);
		when(employeeRepository.findById(4L)).thenReturn(Optional.empty());
		employeeServiceImpl.updateEmployeeById(4L, updatedEmployee);
		verify(employeeRepository).findById(4L);
	}

	@Test
	public void shouldDeleteEmplyoeeByEmpId() {
		Employee mockEmployee = new Employee(4L, "4th address", 26, "1234567890", "Rajat", "Asati", 11100.0);
		Optional<Employee> empOptional = Optional.of(mockEmployee);
		when(employeeRepository.findById(1L)).thenReturn(empOptional);
		doNothing().when(employeeRepository).deleteById(1L);
		employeeServiceImpl.deleteEmployeeById(1L);
		verify(employeeRepository).deleteById(1L);
	}

	@Test(expected = EmployeeNotFoundException.class)
	public void shouldThrowEmployeeNotFoundExceptionIfEmployeeNotFoundByEmpId() {
		when(employeeRepository.findById(4L)).thenReturn(Optional.empty());
		employeeServiceImpl.deleteEmployeeById(4L);
		verify(employeeRepository).deleteById(4L);
	}

	private List<Employee> mockEmployeesData() {
		return Arrays.asList(new Employee(1L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0),
				new Employee(2L, "Omaha", 89, "1234567890", "Warren", "Buffett", 1000000.0),
				new Employee(3L, "Median", 63, "0987654321", "Bill", "Gates", 1000000.0),
				new Employee(4L, "New York City", 55, "6574893021", "Jeff", "Bezos", 1000000.0),
				new Employee(5L, "Los Angeles", 48, "1092837465", "Elon", "Musk", 1000000.0));
	}

	private Employee mockEmployeeData() {
		return new Employee(1L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0);
	}

}
