package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.Room;
import com.SE.RoomBook.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class RoomController {

    @Autowired
    private final RoomRepository RoomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.RoomRepository = roomRepository;
    }

    @GetMapping("/Room")
    public Collection<Room> Rooms() {
        return RoomRepository.findAll().stream().collect(Collectors.toList());
    }

}
