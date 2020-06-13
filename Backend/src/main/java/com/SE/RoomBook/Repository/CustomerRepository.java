package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;

@RepositoryRestResource
public
interface CustomerRepository extends JpaRepository<Customer, Long> { 
    Customer findById(long id); 
    @Query( value = "SELECT * FROM Customer c where c.Customer_Email = :Customer_Email and c.Password = :Password",
            nativeQuery = true)
            Customer findPatientByCustomer(@Param("Customer_Email") String Email,
            @Param("Password") String Password);
}