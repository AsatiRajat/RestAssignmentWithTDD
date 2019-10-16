package com.yash.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.rest.controller.EmployeeController;
import com.yash.rest.models.Employee;
import com.yash.rest.service.EmployeeService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class, secure = false)
// @RunWith(SpringRunner.class)
// @SpringBootTest
// @AutoConfigureMockMvc
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private EmployeeController employeeController;

	@MockBean
	private EmployeeService employeeService;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void shouldReturnAllEmployeesInJsonFormat() throws Exception {

		List<Employee> mockEmployees = mockEmployeesData();
		when(employeeService.getEmployees()).thenReturn(mockEmployees);
		employeeController.getEmployees();
		verify(employeeService).getEmployees();

		mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].empId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].age").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].address").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].salary").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].salary").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].salary").isNumber());
	}

	@Test
	public void shouldReturnAllEmployeesInXMLFormatTest() throws Exception {

		List<Employee> mockEmployees = mockEmployeesData();
		when(employeeService.getEmployees()).thenReturn(mockEmployees);
		employeeController.getEmployees();
		verify(employeeService).getEmployees();

		mockMvc.perform(get("/employees").accept(MediaType.APPLICATION_XML)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.xpath("List/item/empId").exists())
				.andExpect(MockMvcResultMatchers.xpath("List/item/age").string("24"))
				.andExpect(MockMvcResultMatchers.xpath("List/item/address").string("Pune"))
				.andExpect(MockMvcResultMatchers.xpath("List/item/firstName").exists())
				.andExpect(MockMvcResultMatchers.xpath("List/item/firstName").string("Rajat"))
				.andExpect(MockMvcResultMatchers.xpath("List/item/salary").exists())
				.andExpect(MockMvcResultMatchers.xpath("List/item/salary").number(10000.0));
	}

	@Test
	public void shouldReturnNewEmployeeTest() throws Exception {

		Employee newEmployee = new Employee(null, "address", 24, "1234567890", "Rajat", "Asati", 1000.0);
		Employee mockEmployee = mockEmployeeData();
		when(employeeService.createEmployee(newEmployee)).thenReturn(mockEmployee);
		employeeController.addEmployee(newEmployee);
		verify(employeeService).createEmployee(newEmployee);

		RequestBuilder requestBuilder = post("/employees").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newEmployee));

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk()).andReturn();

	}

	@Test
	public void shouldThrowExceptionResoponseIfAnyIssueOccurForAddEmployee() throws Exception {

		Employee newEmployee = new Employee(null, "address", 24, "1234567890", "Rajat", "Asati", 1000.0);

		RequestBuilder requestBuilder = post("/employees").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(newEmployee));

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
				.andExpect(jsonPath("$.message", is("Employee does not exist.")));
	}

	@Test
	public void shouldReturnEmployeeByIdInJsonFormatTest() throws Exception {

		Employee mockEmployee = mockEmployeeData();
		when(employeeService.findEmployeeById(1L)).thenReturn(mockEmployee);
		employeeController.getEmployee(1L);
		verify(employeeService).findEmployeeById(1L);

		mockMvc.perform(get("/employees/1").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.empId").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.age").value(24))
				.andExpect(MockMvcResultMatchers.jsonPath("$.address").value("Pune"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Rajat"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").isNumber())
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000.0));
	}

	@Test
	public void shouldReturnEmployeeByIdInXMLFormatTest() throws Exception {

		Employee mockEmployee = mockEmployeeData();
		when(employeeService.findEmployeeById(1L)).thenReturn(mockEmployee);
		employeeController.getEmployee(1L);
		verify(employeeService).findEmployeeById(1L);

		mockMvc.perform(get("/employees/1").accept(MediaType.APPLICATION_XML)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.xpath("Employee/empId").exists())
				.andExpect(MockMvcResultMatchers.xpath("Employee/age").string("24"))
				.andExpect(MockMvcResultMatchers.xpath("Employee/address").string(mockEmployee.getAddress()))
				.andExpect(MockMvcResultMatchers.xpath("Employee/firstName").exists())
				.andExpect(MockMvcResultMatchers.xpath("Employee/firstName").string(mockEmployee.getFirstName()))
				.andExpect(MockMvcResultMatchers.xpath("Employee/salary").exists())
				.andExpect(MockMvcResultMatchers.xpath("Employee/salary").number(mockEmployee.getSalary()));
	}

	@Test
	public void shouldThrowExceptionResoponseIfAnyIssueOccurForGetEmployeeByIdIsUnknown() throws Exception {

		RequestBuilder requestBuilder = get("/employees/123").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
				.andExpect(jsonPath("$.message", is("Employee does not exist.")));
	}

	@Test
	public void shouldThrowExceptionResoponseIfAnyIssueOccurForGetEmployeeByIdIsInValid() throws Exception {

		RequestBuilder requestBuilder = get("/employees/abc").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.value())))
				.andExpect(jsonPath("$.message", is("Bad Request")));
	}

	@Test
	public void shouldReturnUpdatedEmployeeTest() throws Exception {

		Employee updatedEmployee = new Employee(null, "updated address", 1, "1234567890", "Asati", "Rajat", 1000.0);
		Employee mockEmployee = mockEmployeeData();
		when(employeeService.updateEmployeeById(1l, updatedEmployee)).thenReturn(mockEmployee);
		employeeController.updateEmployee(1l, updatedEmployee);
		verify(employeeService).updateEmployeeById(1l, updatedEmployee);

		RequestBuilder requestBuilder = put("/employees/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedEmployee));

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isOk());

	}

	@Test
	public void shouldThrowExceptionResoponseIfAnyIssueOccurForUpdateEmployee() throws Exception {

		Employee updateEmployee = new Employee(null, "address", 24, "1234567890", "Rajat", "Asati", 1000.0);

		RequestBuilder requestBuilder = put("/employees/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(updateEmployee));

		mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status", is(HttpStatus.NOT_FOUND.value())))
				.andExpect(jsonPath("$.message", is("Employee does not exist.")));
	}

	@Test
	public void shouldDeleteEmployeeByIdTest() throws Exception {

		doNothing().when(employeeService).deleteEmployeeById(3L);
		employeeController.deleteEmployee(3L);
		verify(employeeService).deleteEmployeeById(3L);

		mockMvc.perform(delete("/employees/3").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

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
