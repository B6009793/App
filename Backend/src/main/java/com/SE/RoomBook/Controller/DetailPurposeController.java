package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.DetailPurpose;
import com.SE.RoomBook.Repository.DetailPurposeRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class DetailPurposeController {

    @Autowired
    private final DetailPurposeRepository detailPurposeRepository;

    public DetailPurposeController(DetailPurposeRepository detailPurposeRepository) {
        this.detailPurposeRepository = detailPurposeRepository;
    }

    @GetMapping("/DetailPurpose")
    public Collection<DetailPurpose> DetailPurposes() {
        return detailPurposeRepository.findAll().stream().collect(Collectors.toList());
    }

}
