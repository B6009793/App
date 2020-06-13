package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class EquipmentNameController {

    @Autowired
    private final EquipmentNameRepository EquipmentNameRepository;

    public EquipmentNameController(EquipmentNameRepository EquipmentNameRepository) {
        this.EquipmentNameRepository = EquipmentNameRepository;
    }

    @GetMapping("/equipmentNames")
    public Collection<EquipmentName> EquipmentName() {
        return EquipmentNameRepository.findAll().stream().collect(Collectors.toList());
    }

    // @GetMapping("/EquipmentName/{equipmentname_id}")
    // public Optional<EquipmentName> EquipmentName(@PathVariable Long equipmentname_id) {
    //     Optional<EquipmentName> EquipmentName = EquipmentNameRepository.findById(equipmentname_id);
    //     return EquipmentName;
    // }

}
