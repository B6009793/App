package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.StatusCustomer;
import com.SE.RoomBook.Repository.StatusCustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class StatusCustomerController {

    @Autowired
    private final StatusCustomerRepository statusCustomerRepository;

    public StatusCustomerController(StatusCustomerRepository statusCustomerRepository) {
        this.statusCustomerRepository = statusCustomerRepository;
    }

    @GetMapping("/statusCustomer")
    public Collection<StatusCustomer> StatusCustomers() {
        return statusCustomerRepository.findAll().stream().collect(Collectors.toList());
    }

}
