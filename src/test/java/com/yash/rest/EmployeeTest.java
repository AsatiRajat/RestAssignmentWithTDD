package com.yash.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.yash.rest.models.Employee;

import nl.jqno.equalsverifier.EqualsVerifier;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeTest {

//	@Test
//	public void hashCodeReturnTrueTest() {
//		// equals and hashCode check name
//		Employee empObj1 = new Employee(1L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0);
//		Employee empObj2 = new Employee(1L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0);
//		assertTrue(empObj1.equals(empObj2) && empObj2.equals(empObj1));
//		assertTrue(empObj1.hashCode() == empObj2.hashCode());
//		assertTrue(empObj1.getEmpId().equals(empObj2.getEmpId()) && empObj2.getEmpId().equals(empObj1.getEmpId()));
//	}
//
//	@Test
//	public void hashCodeReturnFalseTest() {
//		Employee empObj1 = new Employee(1L, "Pune", 24, "9425159443", "Rajat", "Asati", 10000.0);
//		Employee empObj2 = new Employee(2L, "Mumbai", 25, "9425159443", "Rajat", "Asati", 10000.0);
//		assertFalse(empObj1.equals(empObj2) && empObj2.equals(empObj1));
//		assertFalse(empObj1.hashCode() == empObj2.hashCode());
//	}
	
	@Test
	public void equalsContract() {
	    EqualsVerifier.forClass(Employee.class).verify();
	}

}
