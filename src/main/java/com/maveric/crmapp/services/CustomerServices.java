package com.maveric.crmapp.services;
import com.maveric.crmapp.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crmapp.pojos.Customer;

import java.util.List;

public interface CustomerServices {

    Customer acceptCustomerDetails(Customer customer);
    void updateCustomerDetails(Customer customer)throws CustomerDetailsNotFoundException;
    void removeCustomerDetails(int id)throws CustomerDetailsNotFoundException;

    Customer getCustomerDetails(int id) throws CustomerDetailsNotFoundException;
    Customer getCustomerDetailsByEmailId(String emailId)throws CustomerDetailsNotFoundException;

    List<Customer> getCustomerDetailsByFirstName(String firstName) throws CustomerDetailsNotFoundException;
    List<Customer> getCustomerDetailsByLastName(String  lastName)throws CustomerDetailsNotFoundException;
    List<Customer> getCustomerDetailsByAge(int age) throws CustomerDetailsNotFoundException;
    List<Customer> getCustomerDetailsByGender(String gender) throws CustomerDetailsNotFoundException;
    List<Customer> getAllCustomerDetails() throws CustomerDetailsNotFoundException;
    Customer getCustomerDetailsByFirstNameAndEmailId(String firstName, String emailId) throws CustomerDetailsNotFoundException;
}