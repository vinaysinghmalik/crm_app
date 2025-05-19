package com.maveric.crmapp.test;

import com.maveric.crmapp.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crmapp.pojos.Customer;
import com.maveric.crmapp.repositories.CustomerRepository;
import com.maveric.crmapp.services.CustomerServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;


@ExtendWith(SpringExtension.class) //Spring Way (Auto)
@SpringBootTest(classes = com.maveric.crmapp.CrmAppApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CrmAppApplicationTests{

	//Spring Way(Spring framework will use internally Mockito framework to create mock object)
	@MockBean
	CustomerRepository customerRepository;

	@Autowired
	CustomerServices customerServices;

	//Stubbing: giving dummy behaviour to repo methods

	@BeforeEach
	public void setUpTestEnv(){

		Customer customer1 = new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23);
		Customer customer2 = new Customer(2, "Siya", "Dash", "Female", "sd@gmail.com", 21);
		Customer customer3 = new Customer(3, "Amrit", "Verma", "Male", "av@gmail.com", 23);
		Customer customer4 = new Customer(4, "Vinay", "Singh", "Male", "vs@gmail.com", 24);
		Customer customer5 = new Customer(5, "Shreya", "Patel", "Female", "sp@gmail.com", 21);

		//getById
		Mockito.when(customerRepository.findById(1)).thenReturn(Optional.of(customer1));
		Mockito.when(customerRepository.findById(2)).thenReturn(Optional.of(customer2));
		Mockito.when(customerRepository.findById(3)).thenReturn(Optional.of(customer3));
		Mockito.when(customerRepository.findById(1103)).thenReturn(Optional.ofNullable(null));

		//save
		Mockito.when(customerRepository.save(new Customer("Vinay", "Malik", "Male", "vm@gmail.com", 23))).thenReturn(customer1);
		Mockito.when(customerRepository.save(new Customer("Siya", "Dash", "Female", "sd@gmail.com", 21))).thenReturn(customer2);
		Mockito.when(customerRepository.save(new Customer("Amrit", "Verma", "Male", "av@gmail.com", 23))).thenReturn(customer3);
		Mockito.when(customerRepository.save(new Customer("Vinay", "Singh", "Male", "vs@gmail.com", 24))).thenReturn(customer4);
		Mockito.when(customerRepository.save(new Customer("Shreya", "Patel", "Female", "sp@gmail.com", 21))).thenReturn(customer5);

		//findAll
		List<Customer> allCustomers = List.of(customer1, customer2, customer3, customer4,customer5);
		Mockito.when(customerRepository.findAll()).thenReturn(allCustomers);

		//findByFirstName
		Mockito.when(customerRepository.findByFirstName("Vinay")).thenReturn(List.of(customer1));
		Mockito.when(customerRepository.findByFirstName("Unknown")).thenReturn(Collections.emptyList());

		//findByLastName
		Mockito.when(customerRepository.searchByLastName("Malik")).thenReturn(List.of(customer1));
		Mockito.when(customerRepository.searchByLastName("Unavailable")).thenReturn(Collections.emptyList());

		//findByFirstNameAndEmailId
		Mockito.when(customerRepository.findByFirstNameAndEmailId("Vinay", "vm@gmail.com")).thenReturn(customer1);
		Mockito.when(customerRepository.findByFirstNameAndEmailId("Invalid", "wrong@example.com")).thenReturn(null);

		//findByEmailId
		Mockito.when(customerRepository.findByEmailId("vm@gmail.com")).thenReturn(customer1);
		Mockito.when(customerRepository.findByEmailId("wrong@example.com")).thenReturn(null);

		//findByAge
		Mockito.when(customerRepository.findByAge(23)).thenReturn(List.of(customer1));
		Mockito.when(customerRepository.findByAge(80)).thenReturn((Collections.emptyList()));

		//findByGender
		Mockito.when(customerRepository.findByGender("Male")).thenReturn(List.of(customer1, customer3, customer4));
		Mockito.when(customerRepository.findByGender("Female")).thenReturn(List.of(customer2, customer5));
		Mockito.when(customerRepository.findByGender("abc")).thenReturn(Collections.emptyList());

		//update
		Customer updatedCustomer = new Customer(2, "Siya", "Dash", "sd@gmail.com", "Female", 21);
		Mockito.when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);
		Mockito.when(customerRepository.save(new Customer(10, "Siya", "Dash", "sd@gmail.com", "Female", 21))).thenReturn(null);

		//deleteById
		doNothing().when(customerRepository).deleteById(4);
		doNothing().when(customerRepository).deleteById(5);
	}


	@Test
	@Order(1)
	void testAcceptCustomers() {
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Vinay", "Malik", "Male", "vm@gmail.com", 23)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Siya", "Dash", "Female", "sd@gmail.com", 21)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Amrit", "Verma", "Male", "av@gmail.com", 23)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Vinay", "Singh", "Male", "vs@gmail.com", 24)));
		Assertions.assertNotNull(customerServices.acceptCustomerDetails(new Customer("Shreya", "Patel", "Female", "sp@gmail.com", 21)));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(Mockito.any(Customer.class));
	}

	@Test
	@Order(2)
	void testGetCustomerById_Positive() throws CustomerDetailsNotFoundException {
		Customer expected = new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23);
		Assertions.assertEquals(expected, customerServices.getCustomerDetails(1));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(1);
	}

	@Test
	@Order(3)
	void testGetCustomerById_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetails(10));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(10);
	}

	@Test
	@Order(4)
	void testGetCustomersByFirstName_Positive() throws CustomerDetailsNotFoundException {
		List<Customer> expected = List.of(new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23));
		Assertions.assertEquals(expected, customerServices.getCustomerDetailsByFirstName("Vinay"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstName("Vinay");
	}

	@Test
	@Order(5)
	void testGetCustomersByFirstName_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByFirstName("xyz"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstName("xyz");
	}

	@Test
	@Order(6)
	void testGetCustomersByLastName_Positive() throws CustomerDetailsNotFoundException {
		List<Customer> expected = List.of(new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23));
		Assertions.assertEquals(expected, customerServices.getCustomerDetailsByLastName("Malik"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).searchByLastName("Malik");
	}

	@Test
	@Order(7)
	void testGetCustomersByLastName_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByLastName("abc"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).searchByLastName("abc");
	}

	@Test
	@Order(8)
	void testGetCustomerByFirstNameAndEmailId_Positive() throws CustomerDetailsNotFoundException {
		Customer expected = new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23);
		Assertions.assertEquals(expected, customerServices.getCustomerDetailsByFirstNameAndEmailId("Vinay", "vm@gmail.com"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstNameAndEmailId("Vinay", "vm@gmail.com");
	}

	@Test
	@Order(9)
	void testGetCustomerByFirstNameAndEmailId_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByFirstNameAndEmailId("Invalid", "wrong@example.com"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByFirstNameAndEmailId("Invalid", "wrong@example.com");
	}

	@Test
	@Order(10)
	void testGetCustomerByEmailId_Positive() throws CustomerDetailsNotFoundException {
		Customer expected = new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23);
		Assertions.assertEquals(expected, customerServices.getCustomerDetailsByEmailId("vm@gmail.com"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByEmailId("vm@gmail.com");
	}

	@Test
	@Order(11)
	void testGetCustomerByEmailId_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByEmailId("wrong@example.com"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByEmailId("wrong@example.com");
	}

	@Test
	@Order(12)
	void testGetAllCustomersByAge_Positive() throws CustomerDetailsNotFoundException {
		List<Customer> expected = List.of(new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23));
		Assertions.assertEquals(expected, customerServices.getCustomerDetailsByAge(23));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAge(23);
	}

	@Test
	@Order(13)
	void testGetAllCustomersByAge_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByAge(99));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByAge(99);
	}

	@Test
	@Order(14)
	void testGetAllCustomersByGender_Positive() throws CustomerDetailsNotFoundException {
		List<Customer> maleExpected = List.of(
				new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23),
				new Customer(3, "Amrit", "Verma", "Male", "av@gmail.com", 23),
				new Customer(4, "Vinay", "Singh", "Male", "vs@gmail.com", 24)
		);
		List<Customer> femaleExpected = List.of(
				new Customer(2, "Siya", "Dash", "Female", "sd@gmail.com", 21),
				new Customer(5, "Shreya", "Patel", "Female", "sp@gmail.com", 21)
		);

		Assertions.assertEquals(maleExpected, customerServices.getCustomerDetailsByGender("Male"));
		Assertions.assertEquals(femaleExpected, customerServices.getCustomerDetailsByGender("Female"));

		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByGender("Male");
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByGender("Female");
	}

	@Test
	@Order(15)
	void testGetAllCustomersByGender_Negative() {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.getCustomerDetailsByGender("abc"));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findByGender("abc");
	}

	@Test
	@Order(16)
	void testUpdateCustomerDetails_Positive() throws CustomerDetailsNotFoundException {
		Customer input = new Customer(2, "Siya", "Dash", "sd@gmail.com", "Female", 21);
		Assertions.assertEquals("Successfully Updated", customerServices.updateCustomerDetails(input));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).save(input);
	}

	@Test
	@Order(17)
	void testUpdateCustomerDetails_Negative(){
		Customer invalid = new Customer(101, "Amrit", "Verma", "av@gmail.com", "Male", 18);
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.updateCustomerDetails(invalid));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(invalid.getId());
	}

	@Test
	@Order(18)
	void testDeleteCustomer_Positive()throws CustomerDetailsNotFoundException {
		customerServices.removeCustomerDetails(1);
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).deleteById(1);
	}

	@Test
	@Order(19)
	void testDeleteCustomer_Negative()throws CustomerDetailsNotFoundException {
		Assertions.assertThrows(CustomerDetailsNotFoundException.class, () -> customerServices.removeCustomerDetails(999));
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findById(999);
	}

	@Test
	@Order(20)
	void testGetAllCustomers_Positive() throws CustomerDetailsNotFoundException {
		List<Customer> expected = List.of(
				new Customer(1, "Vinay", "Malik", "Male", "vm@gmail.com", 23),
				new Customer(2, "Siya", "Dash", "Female", "sd@gmail.com", 21),
				new Customer(3, "Amrit", "Verma", "Male", "av@gmail.com", 23),
				new Customer(4, "Vinay", "Singh", "Male", "vs@gmail.com", 24),
				new Customer(5, "Shreya", "Patel", "Female", "sp@gmail.com", 21)
		);
		Assertions.assertEquals(expected, customerServices.getAllCustomerDetails());
		Mockito.verify(customerRepository, Mockito.atLeastOnce()).findAll();
	}
}
