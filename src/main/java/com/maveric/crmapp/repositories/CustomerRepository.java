package com.maveric.crmapp.repositories;

import com.maveric.crmapp.pojos.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {    // Data Access Layer
    List<Customer> findByFirstName(String firstName);

    List<Customer> searchByLastName(String last);
    Customer findByEmailId(String emailId);
    List<Customer> findByAge(int age);
    List<Customer> findByGender(String gender);
    Customer findByFirstNameAndEmailId(String firstName, String emailId);

}
