package com.SE.RoomBook.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import com.SE.RoomBook.Entity.Status;
import com.SE.RoomBook.Repository.StatusRepository;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class StatusController {

    @Autowired
    private final StatusRepository StatusRepository;

    public StatusController(StatusRepository statusRepository) {
        this.StatusRepository = statusRepository;
    }

    @GetMapping("/Status")
    public Collection<Status> Statuss() {
        return StatusRepository.findAll().stream().collect(Collectors.toList());
    }
}
