package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Repository.PurposeRoomRepository;
import com.SE.RoomBook.Entity.PurposeRoom;
 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class PurposeRoomController {

    @Autowired
    private final PurposeRoomRepository purposeRoomRepository;

    PurposeRoomController(PurposeRoomRepository purposeRoomRepository) {
        this.purposeRoomRepository = purposeRoomRepository;
    }

    @GetMapping("/PurposeRoom")
    public Collection<PurposeRoom> PurposeRooms(){
        return purposeRoomRepository.findAll().stream().collect(Collectors.toList());
    }
}
