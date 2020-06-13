package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController

public class DeleteReservationController {

    @Autowired
    private final ReservationsRepository reservationsRepository;

    @Autowired
    private StatusReservationRepository statusReservationRepository;

    public DeleteReservationController(ReservationsRepository reservationsRepository,
            StatusReservationRepository statusReservationRepository) {
        this.reservationsRepository = reservationsRepository;
        this.statusReservationRepository = statusReservationRepository;
    }

    // เรียก list Reservation ทั้งหมด ที่มี confrim = true
    @GetMapping("/reservationCustomerId/{customerId}")
    public Collection<Reservations> getResvationAlllistCustomer(@PathVariable("customerId") Long id) {

        return reservationsRepository.findCustomerofReservationByIdAlllist(id).stream().collect(Collectors.toList());
    }

    // แก้ไข Reservation ที่ต้องการเปลี่ยน ให้เป็นยกเลิกการจอง
    @PutMapping("/reservationCustomer/{reservationid}")
    public Reservations updateReservation(@PathVariable("reservationid") Long id) {

        Reservations rchange = reservationsRepository.findReservationById(id);
        StatusReservation statusChange = statusReservationRepository.findCancelById();

        rchange.setStatusReservation(statusChange);
        return reservationsRepository.save(rchange);
    }
}
