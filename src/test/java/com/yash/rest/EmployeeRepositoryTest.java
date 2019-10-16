package com.yash.rest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.yash.rest.models.Employee;
import com.yash.rest.repositiory.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EmployeeRepositoryTest {

	public EmployeeRepositoryTest() {
	}

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Test
	public void shouldReturnNewAddedEmployee() {

		// add
		Employee employee = new Employee(null, "4th address", 26, "1234567890", "rajat3101", "Asati", 11100.0);
		entityManager.persist(employee);
		entityManager.flush();

		// when
		Employee found = employeeRepository.findByFirstName(employee.getFirstName());

		// then
		assertEquals(employee.getFirstName(), found.getFirstName());

	}

}