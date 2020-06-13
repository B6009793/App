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

import com.SE.RoomBook.Entity.Room;
import com.SE.RoomBook.Entity.ManageStatus;
import com.SE.RoomBook.Entity.Status;
import com.SE.RoomBook.Entity.Employee;
import com.SE.RoomBook.Repository.RoomRepository;
import com.SE.RoomBook.Repository.ManageStatusRepository;
import com.SE.RoomBook.Repository.StatusRepository;
import com.SE.RoomBook.Repository.EmployeeRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import javax.persistence.*;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class ManageStatusController {
    @Autowired
    private final ManageStatusRepository ManageStatusRepository;
    @Autowired
    private RoomRepository RoomRepository;
    @Autowired
    private StatusRepository StatusRepository;
    @Autowired
    private EmployeeRepository EmployeeRepository;

    ManageStatusController(ManageStatusRepository ManageStatusRepository) {
        this.ManageStatusRepository = ManageStatusRepository;
    }

    @GetMapping("/ManageStatus")
    public Collection<ManageStatus> ManageStatuss() {
        return ManageStatusRepository.findAll().stream().collect(Collectors.toList());
    }

    @GetMapping("/ManageStatusD")
    public Collection<ManageStatus> ManageStatussFind() {
        return ManageStatusRepository.findSellectRoomOnline();
    }

    @GetMapping("/ManageStatusRoom/{Room_id}")
    public ManageStatus newManageStatus(@PathVariable long Room_id) {

        return ManageStatusRepository.findRoomNull(Room_id);
    }

   @GetMapping("/StatusRoom")
    public Collection<ManageStatus> ManageStatussFindStatusRoom() {
        return ManageStatusRepository.findSellectStatusRoom();
    }

    @PostMapping("/ManageStatus/{Room_id}/{Status_id}/{Employee_id}/{ManageStatus_note}")
    public ManageStatus newManageStatus(ManageStatus newManageStatus, @PathVariable long Room_id,
            @PathVariable long Status_id, @PathVariable long Employee_id,@PathVariable String ManageStatus_note) {

        Room room = RoomRepository.findById(Room_id);
        Status status = StatusRepository.findById(Status_id);
        Employee employee = EmployeeRepository.findById(Employee_id);

        ManageStatus Ms = ManageStatusRepository.findRoomNull(Room_id);
        if (Ms != null) {
            Ms.setDelete_at(new Date());
            ManageStatusRepository.save(Ms);
            // ManageStatus Mss = ManageStatusRepository.save(Ms);
        }

        newManageStatus.setRoom(room);
        newManageStatus.setStatus(status);
        newManageStatus.setEmployee(employee);

        newManageStatus.setCreate_at(new Date());
        newManageStatus.setDelete_at(null);
        newManageStatus.setManageStatus_note(ManageStatus_note);


        return ManageStatusRepository.save(newManageStatus); // บันทึก Objcet ชื่อ ManageStatus
    }
}
