package com.maveric.crmapp.controllers;
import com.maveric.crmapp.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crmapp.pojos.Customer;
import com.maveric.crmapp.services.CustomerServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerServices customerServices;


    //Create Customer
    @PostMapping(value = "/v1/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> acceptCustomerDetails (@Valid @RequestBody Customer customerToBeInsert){
        Customer insertedCustomer = customerServices.acceptCustomerDetails(customerToBeInsert);
        return new ResponseEntity<>(insertedCustomer, HttpStatus.CREATED);
    }

    //Delete Customer by ID
    @DeleteMapping("/v1/customer/delete/{id}")
    public ResponseEntity<String> deleteCustomerDetails(@PathVariable int id) throws CustomerDetailsNotFoundException {
        customerServices.removeCustomerDetails(id);
        return new ResponseEntity<>("Successfully Deleted Customer", HttpStatus.OK);
    }

    //Get All Customer
    @GetMapping("/v1/customer")
    public ResponseEntity<List<Customer>> getAllCustomerDetails() throws CustomerDetailsNotFoundException{
        List<Customer> customerToBeFound = customerServices.getAllCustomerDetails();
        return new ResponseEntity<>(customerToBeFound, HttpStatus.OK);
    }


    // Get Customer by ID
    @GetMapping("/v1/customer/id/{id}")
    public ResponseEntity<Customer> getCustomerDetails(@PathVariable int id) throws CustomerDetailsNotFoundException {
        Customer customerToBeFound = customerServices.getCustomerDetails(id);
        return new ResponseEntity<>(customerToBeFound,HttpStatus.OK);
    }


    //Get all Customer By Age
    @GetMapping("/v1/customer/age/{age}")
    public ResponseEntity <List<Customer>> getCustomerDetailsByAge(@PathVariable int age) throws CustomerDetailsNotFoundException {
        List<Customer> customersToBeFound = customerServices.getCustomerDetailsByAge(age);
        return new ResponseEntity<>(customersToBeFound, HttpStatus.OK);
    }


    //Get all Customer By Gender
    @GetMapping("/v1/customer/gender/{gender}")
    public ResponseEntity <List<Customer>> getCustomerDetailsByGender(@PathVariable String gender) throws CustomerDetailsNotFoundException {
        List<Customer> customersToBeFound = customerServices.getCustomerDetailsByGender(gender);
        return new ResponseEntity<>(customersToBeFound, HttpStatus.OK);
    }


    //Get Customer By EmailId
    @GetMapping("/v1/customer/emailId/{emailId}")
    public ResponseEntity<Customer> getCustomerDetailsByEmailId(@PathVariable String emailId) throws CustomerDetailsNotFoundException {
        Customer customerToBeFound = customerServices.getCustomerDetailsByEmailId(emailId);
        return new ResponseEntity<>(customerToBeFound,HttpStatus.OK);
    }


    //Get all Customer by First Name
    @GetMapping("/v1/customer/firstName/{firstName}")
    public ResponseEntity <List<Customer>> getCustomerDetailsByFirstName(@PathVariable String firstName) throws CustomerDetailsNotFoundException {
        List<Customer> customersToBeFound = customerServices.getCustomerDetailsByFirstName(firstName);
        return new ResponseEntity<>(customersToBeFound, HttpStatus.OK);
    }


    //Get Customer By Last Name
    @GetMapping("/v1/customer/lastName/{lastName}")
    public ResponseEntity <List<Customer>> getCustomerDetailsByLastName(@PathVariable String lastName) throws CustomerDetailsNotFoundException {
        List<Customer> customersToBeFound = customerServices.getCustomerDetailsByLastName(lastName);
        return new ResponseEntity<>(customersToBeFound, HttpStatus.OK);
    }


    //Update Customer By ID
    @PutMapping("/v1/customer/update/{customer}")
    public ResponseEntity<String> updateCustomerDetails(@Valid @RequestBody Customer customer) throws CustomerDetailsNotFoundException {
        return new ResponseEntity<>(customerServices.updateCustomerDetails(customer), HttpStatus.OK);
    }


    @GetMapping("/v1/customer/firstName/{firstName}/gender/{gender}")
    public ResponseEntity<Customer> getCustomerDetailsByFirstNameAndGender(@PathVariable String firstName, @PathVariable String gender) throws CustomerDetailsNotFoundException{
        Customer customerToBeFound = customerServices.getCustomerDetailsByFirstNameAndGender(firstName,gender);
        return new ResponseEntity<>(customerToBeFound, HttpStatus.OK);
    }




//    @GetMapping("v1/customer/firstName/{firstName}/emailId/{emailId}")
//    public ResponseEntity<Customer> getCustomerDetailsByFirstNameAndEmailId(@PathVariable String firstName, @PathVariable String emailId) throws CustomerDetailsNotFoundException {
//        Customer customerToBeFound = customerServices.getCustomerDetailsByFirstNameAndEmailId(firstName,emailId);
//        return new ResponseEntity<>(customerToBeFound,HttpStatus.OK);
//    }




}