package com.maveric.crmapp.test;

import com.maveric.crmapp.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crmapp.pojos.Customer;
import com.maveric.crmapp.services.CustomerServices;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.maveric.crmapp.CrmAppApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrmAppApplicationTests {

	@Autowired
	CustomerServices customerServices;


	@Test
	public void test1(){
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Vinay", "Malik", "Male", "vm@gmail.com", 24)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Siya", "Dash", "Female", "sd@gmail.com", 23)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Amrit", "Verma", "Male", "av@gmail.com", 22)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Shreya", "Patel", "Female", "sp@gmail.com",22)));
	}

	@Test
	public void test2() throws CustomerDetailsNotFoundException{
		Customer expectedCustomer = new Customer(1,"Vinay", "Malik", "Male", "vm@gmail.com", 24);
		Assertions.assertEquals(expectedCustomer, customerServices.getCustomerDetails(1));
	}

	@Test
	public void test3(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetails(6));
	}

	@Test
	public void test4() throws CustomerDetailsNotFoundException {
		Assertions.assertFalse(customerServices.getCustomerDetailsByFirstName("Vinay").isEmpty());
	}

	@Test
	public void test5(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByFirstName("Aman"));
	}

	@Test
	public void test6() throws CustomerDetailsNotFoundException {
		Assertions.assertNotNull(customerServices.getCustomerDetailsByGender("Male"));
	}

	@Test
	public void test7(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByGender("Not Defined"));
	}

	@Test
	public void test8() throws CustomerDetailsNotFoundException {
		Assertions.assertNotNull(customerServices.getCustomerDetailsByAge(24));
	}

	@Test
	public void test9(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByAge(21));
	}

	@Test
	public void test10(){
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByEmailId("as@gmail.com"));
	}
}
