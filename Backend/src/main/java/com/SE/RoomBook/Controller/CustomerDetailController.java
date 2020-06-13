package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.Customer;
import com.SE.RoomBook.Repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class CustomerDetailController {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerDetailController(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    // @GetMapping("profile/customer")
    // public Collection<Customer> Customers() {
    // return customerRepository.findAll().stream().collect(Collectors.toList());
    // }

    @GetMapping("/customer/{Cus_id}")
    public Customer getCustomersDetail(@PathVariable("Cus_id") final long id) {
    return customerRepository.findById(id);
    }

}
