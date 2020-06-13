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
import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class ReturnsController {
    @Autowired
    private ManageEquipmentRepository manageEquipmentRepository;
    @Autowired
    private final ReturnsRepository returnsRepository;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    ReturnsController(final ReturnsRepository returnsRepository) {
        this.returnsRepository = returnsRepository;
    }

    @GetMapping("/returns")
    public Collection<Returns> Returns() {
        return returnsRepository.findAll().stream().collect(Collectors.toList());
    }

    @PostMapping("/returns/{C}/{bid}/{E}/{man}/{N}")
    public Returns newReturns(final Returns newReturns, @PathVariable final long C, 
    @PathVariable final long bid, 
    @PathVariable final long E,
    @PathVariable final long man,
    @PathVariable final String N) {

        final Employee employee = employeeRepository.findById(E);
        final Borrow borrow = borrowRepository.findById(bid);
        final Customer customer = customerRepository.findById(C);

        final ManageEquipment  m = manageEquipmentRepository.findById(man);  
        m.setManageEquipment_amount(m.getManageEquipment_amount()+1);
        manageEquipmentRepository.save(m);

        borrow.setBorrowStatus(false);
        newReturns.setBorrow(borrow);
        newReturns.setCustomer(customer);
        newReturns.setEmployee(employee);
        newReturns.setReturnsdate(new Date());
        newReturns.setNote(N);
        borrowRepository.save(borrow);
        return returnsRepository.save(newReturns);

    }
}
