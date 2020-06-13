package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.TitleName;
import com.SE.RoomBook.Repository.TitleNameRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class TitleNameController {

    @Autowired
    private final TitleNameRepository titlenameRepository;

    public TitleNameController(TitleNameRepository titlenameRepository) {
        this.titlenameRepository = titlenameRepository;
    }

    @GetMapping("/title")
    public Collection<TitleName> TitleNames() {
        return titlenameRepository.findAll().stream().collect(Collectors.toList());
    }

}
