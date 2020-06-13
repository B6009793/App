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
public class EquipmentTypeController {

    @Autowired
    private final EquipmentTypeRepository EquipmentTypeRepository;

    public EquipmentTypeController(EquipmentTypeRepository EquipmentTypeRepository) {
        this.EquipmentTypeRepository = EquipmentTypeRepository;
    }

    @GetMapping("/equipmentTypes")
    public Collection<EquipmentType> EquipmentType() {
        return EquipmentTypeRepository.findAll().stream().collect(Collectors.toList());
    }

    // @GetMapping("/EquipmentType/{equipmenttype_id}")
    // public Optional<EquipmentType> EquipmentType(@PathVariable Long equipmenttype_id) {
    //     Optional<EquipmentType> EquipmentType = EquipmentTypeRepository.findById(equipmenttype_id);
    //     return EquipmentType;
    // }

}
