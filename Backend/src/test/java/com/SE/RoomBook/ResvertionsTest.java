package com.SE.RoomBook;

import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * ResvertionsTest
 */
@DataJpaTest
public class ResvertionsTest {
    private Validator validator;

    @Autowired
    private ReservationsRepository reservationsRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void B6014681_testFullFieldDataResvertion() throws ParseException {
        Reservations r = new Reservations();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        LocalDateTime futureDate = LocalDateTime.now();
        futureDate = futureDate.plusHours(1);
        r.setBookdate(date);
        r.setStartTime(futureDate);
        r.setEndTime(futureDate);
        r = reservationsRepository.saveAndFlush(r);
        Optional<Reservations> found = reservationsRepository.findById(r.getReservations_id());

        System.out.println(found.get().getBookdate());

        // ค่าออกมาจะต้องได้ตัวเดิม
        assertEquals(date, found.get().getBookdate());
        // เพื่อให้แน่ใจว่าค่าที่ออกมาจะต้องเป็นตัวเดิม
        assertEquals(sdf.parse("31-08-1982 10:20:56"), found.get().getBookdate());

        assertEquals(futureDate, found.get().getStartTime());
        assertEquals(futureDate, found.get().getEndTime());
    }

    @Test
    void B6014681_testfieldStartTimeResvertionMustBeNotNull() throws ParseException {
        // เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        LocalDateTime d = LocalDateTime.now();
        // สร้าง Reservation และทำการใส่ เวลา (Date and LocalDate)
        Reservations r = new Reservations();
        d = d.plusHours(2);
        r.setBookdate(date);
        r.setEndTime(d);
        r.setStartTime(null);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Reservations>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Reservations> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("StartTime", v.getPropertyPath().toString());
    }

    @Test
    void B6014681_testfieldStartTimeNoFutureResvertion() throws ParseException {
        // เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        LocalDateTime d = LocalDateTime.now();
        LocalDateTime dayNext = d.plusDays(1);
        // สร้าง Reservation และทำการใส่ เวลา (Date and LocalDate)
        Reservations r = new Reservations();
        r.setBookdate(date);
        r.setEndTime(dayNext);
        r.setStartTime(d);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Reservations>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Reservations> v = result.iterator().next();
        assertEquals("must be a future date", v.getMessage());
        assertEquals("StartTime", v.getPropertyPath().toString());
    }

    @Test
    void B6014681_testfieldEndTimeResvertionMustBeNotNull() throws ParseException {
        // เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        LocalDateTime d = LocalDateTime.now();
        d = d.plusHours(1);
        // สร้าง Reservation และทำการใส่ เวลา (Date and LocalDate)
        Reservations r = new Reservations();
        r.setBookdate(date);
        r.setEndTime(null);
        r.setStartTime(d);
        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Reservations>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Reservations> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("EndTime", v.getPropertyPath().toString());
    }

    @Test
    void B6014681_testfielEndTimeNoFutureResvertion() throws ParseException {
        // เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        LocalDateTime d = LocalDateTime.now();
        LocalDateTime dayNext = d.plusDays(1);
        // สร้าง Reservation และทำการใส่ เวลา (Date and LocalDate)
        Reservations r = new Reservations();
        r.setBookdate(date);
        r.setEndTime(d);
        r.setStartTime(dayNext);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Reservations>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Reservations> v = result.iterator().next();
        assertEquals("must be a future date", v.getMessage());
        assertEquals("EndTime", v.getPropertyPath().toString());
    }

    

}