package com.SE.RoomBook.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.net.URLDecoder;

import com.SE.RoomBook.Entity.Customer;
import com.SE.RoomBook.Entity.TitleName;
import com.SE.RoomBook.Entity.Gender;
import com.SE.RoomBook.Entity.StatusCustomer;
import com.SE.RoomBook.Repository.CustomerRepository;
import com.SE.RoomBook.Repository.GenderRepository;
import com.SE.RoomBook.Repository.TitleNameRepository;
import com.SE.RoomBook.Repository.StatusCustomerRepository;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class CustomerController {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private TitleNameRepository titlenameRepository;
    @Autowired
    private GenderRepository genderRepository;
    @Autowired
    private StatusCustomerRepository statusCustomerRepository;

    CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customer")
    public Collection<Customer> Customers() {
        return customerRepository.findAll().stream().collect(Collectors.toList());
    }
    

    @GetMapping("/customer/{Customer_Email}/{Password}")
    public Customer getPatientByName(@PathVariable("Customer_Email") String Email, @PathVariable("Password") String Password) {
        return customerRepository.findPatientByCustomer(Email,Password);
    }

    @PostMapping("/customer/{Name}/{Password}/{Email}/{Phone}/{Title_id}/{Gender_id}/{Status_ID}")
    public Customer newCustomer(Customer newCustomer,
        @PathVariable String Name,
        @PathVariable String Password, 
        @PathVariable String Email,
        @PathVariable long Title_id, 
        @PathVariable long Gender_id,
        @PathVariable long Status_ID,
        @PathVariable String Phone ) {
        

        TitleName TitleNames = titlenameRepository.findById(Title_id);
        StatusCustomer Status = statusCustomerRepository.findById(Status_ID);
        Gender Genders_cus = genderRepository.findById(Gender_id);
        
        newCustomer.setName(Name);
        newCustomer.setPassword(Password);
        newCustomer.setEmail(Email);
        newCustomer.setPhone(Phone);
        newCustomer.setTitleNames(TitleNames);
        newCustomer.setStatuss(Status);
        newCustomer.setGenders(Genders_cus);

        return customerRepository.save(newCustomer); // บันทึก Objcet ชื่อ Customer

    }
}
