package com.SE.RoomBook.Controller;

import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;
import com.SE.RoomBook.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://172.17.0.201:8080")
@RestController
public class ReservationsController { // Tue Aug 31 10:20:56 ICT 1982

    @Autowired
    private PurposeRoomRepository purposeRoomRepository;

    @Autowired
    private final ReservationsRepository reservationsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DetailPurposeRepository detailPurposeRepository;

    @Autowired
    private ManageStatusRepository manageStatusRepository;

    @Autowired
    private StatusReservationRepository statusReservationRepository;

    ReservationsController(ReservationsRepository reservationsRepository, CustomerRepository customerRepository,
            DetailPurposeRepository detailPurposeRepository, PurposeRoomRepository purposeRoomRepository,
            ManageStatusRepository manageStatusRepository, StatusReservationRepository statusReservationRepository) {
        this.detailPurposeRepository = detailPurposeRepository;
        this.reservationsRepository = reservationsRepository;
        this.customerRepository = customerRepository;
        this.purposeRoomRepository = purposeRoomRepository;
        this.manageStatusRepository = manageStatusRepository;
        this.statusReservationRepository = statusReservationRepository;
    }

    @GetMapping("/reservationses") // เลือกดูการจองทั้งหมดที่มี bookconfrim = true
    public Collection<Reservations> reservationss() {
        return reservationsRepository.findByReservationAll().stream().collect(Collectors.toList());
    }

    @GetMapping("/reservationCheckTime/{startTime}/{endTime}/{idroom}")
    public Reservations CheckTime(@PathVariable("startTime") final String startTime,
            @PathVariable("endTime") final String endTime, @PathVariable("idroom") final Long idroom) {
        LocalDate startDate = LocalDate.now();
        String start = startDate.toString() + " " + startTime;
        String end = startDate.toString() + " " + endTime;

        return reservationsRepository.findTime(start, end, idroom);
    }

    @PostMapping("/Reservation") // บันทึกการจองห้องค้นคว้าออนไลน์
    public Reservations index(@RequestBody BodyBook bodyBook) throws ParseException {
        Reservations r = new Reservations();

        Customer c = customerRepository.findById(bodyBook.getCustomerid()).get();
        ManageStatus m = manageStatusRepository.findById(bodyBook.getRoomid()).get();
        StatusReservation s = statusReservationRepository.findConfirmById();
        
        r.setStatusReservation(s);
        r.setManageStatus(m);
        r.setCustomer(c);

        LocalDateTime d = LocalDateTime.parse(LocalDate.now() + "T" + bodyBook.getStart());
        r.setStartTime(d);
        LocalDateTime e = LocalDateTime.parse(LocalDate.now() + "T" + bodyBook.getEnd());
        r.setEndTime(e);
        r.setBookdate(new Date());
        for (Long purpose : bodyBook.getPurosebook()) {
            DetailPurpose dp = new DetailPurpose();
            PurposeRoom p = purposeRoomRepository.findById(purpose).get();
            dp.setPurposeRoom(p);
            dp.setReservations(r);
            detailPurposeRepository.save(dp);
        }

        return reservationsRepository.save(r);
    }

}
