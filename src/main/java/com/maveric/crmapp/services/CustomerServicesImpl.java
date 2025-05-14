package com.maveric.crmapp.services;

import com.maveric.crmapp.exceptions.CustomerDetailsNotFoundException;
import com.maveric.crmapp.pojos.Customer;
import com.maveric.crmapp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("customerService")
public class CustomerServicesImpl implements CustomerServices {

    // Services||business layer depends upon Data access layer


    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer acceptCustomerDetails(Customer customer) {
        return customerRepository.save(customer) ;
    }

    @Override
    public void  updateCustomerDetails(Customer customer) throws CustomerDetailsNotFoundException {
        this.getCustomerDetails(customer.getId());
        customerRepository.save(customer);
    }

    @Override
    public void removeCustomerDetails(int id) throws CustomerDetailsNotFoundException {
        this.getCustomerDetails(id);
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerDetails(int id) throws CustomerDetailsNotFoundException {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isEmpty()) throw  new CustomerDetailsNotFoundException("Customer Details not found for id "+id);
        return  customerOptional.get();
    }

    @Override
    public Customer getCustomerDetailsByEmailId(String emailId) throws CustomerDetailsNotFoundException {
        Customer customer =   customerRepository.findByEmailId(emailId);
        if( customer ==null) throw  new CustomerDetailsNotFoundException("Customer details not found for firstName :  "+emailId);
        return customer;
    }


    @Override
    public List<Customer> getCustomerDetailsByFirstName(String firstName) throws CustomerDetailsNotFoundException {
        List<Customer> customers =   customerRepository.findByFirstName(firstName);
        if( customers.isEmpty()) throw  new CustomerDetailsNotFoundException("Customer details not found for firstName :  "+firstName);
        return customers;
    }

    @Override
    public List<Customer> getAllCustomerDetails() throws CustomerDetailsNotFoundException{
        List<Customer> allCustomerDetails = customerRepository.findAll();
        if(allCustomerDetails.isEmpty())
            throw new CustomerDetailsNotFoundException("No Customer Details Found");
        return allCustomerDetails;
    }

    @Override
    public List<Customer> getCustomerDetailsByLastName(String lastName) throws CustomerDetailsNotFoundException {
        List<Customer> customers =  customerRepository.searchByLastName(lastName);
        if( customers.isEmpty()) throw  new CustomerDetailsNotFoundException("Customers details not found for lastName :  "+lastName);
        return customers;
    }

    @Override
    public List<Customer> getCustomerDetailsByAge(int age) throws CustomerDetailsNotFoundException{
        List<Customer> customers = customerRepository.findByAge(age);
        if(customers.isEmpty()) throw new CustomerDetailsNotFoundException("Customer details not found for age:  " +age);
        return customers;
    }


    @Override
    public List<Customer> getCustomerDetailsByGender(String gender) throws CustomerDetailsNotFoundException{
        List<Customer> customers = customerRepository.findByGender(gender);
        if(customers.isEmpty()) throw new CustomerDetailsNotFoundException("Customer details not found for gender:  " +gender);
        return customers;
    }

    @Override
    public Customer getCustomerDetailsByFirstNameAndEmailId(String firstName, String emailId) throws CustomerDetailsNotFoundException {
        Customer customer = customerRepository.findByFirstNameAndEmailId(firstName, emailId);
        if(customer ==null) throw new CustomerDetailsNotFoundException("Customer details not found for firstName and emailId : " +firstName +emailId);
        return customer;
    }



}