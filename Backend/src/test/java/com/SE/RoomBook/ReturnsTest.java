package com.SE.RoomBook;

import com.SE.RoomBook.Entity.Returns;
import com.SE.RoomBook.Repository.ReturnsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
// import javax.validation.constraints.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.notNull;

/**
 * ResvertionsTest
 */

@DataJpaTest
public class ReturnsTest {
    private Validator validator;

    @Autowired
    private ReturnsRepository returnsRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test//ใส่ข้อมูลครบ
    void B6013394_testFullFieldDataReturns() throws ParseException {
        Returns r = new Returns();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        r.setNote("test");
        r.setReturnsdate(date);
        
        r = returnsRepository.save(r);
        Optional<Returns> found = returnsRepository.findById(r.getId());
        // System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println(found.get().getReturnsdate());
        // System.out.println("\n\n\n\n\n\n\n\n");
        // System.out.println(found.getClass());
        // System.out.println("\n\n\n\n\n\n\n\n");

        // ค่าออกมาจะต้องได้ตัวเดิม
        assertEquals(date, found.get().getReturnsdate());
        // เพื่อให้แน่ใจว่าค่าที่ออกมาจะต้องเป็นตัวเดิม
        assertEquals(sdf.parse("31-08-1982 10:20:56"), found.get().getReturnsdate());
        assertEquals("test", found.get().getNote());
        //assertEquals(futureDate, found.get().getStartTime());
        //assertEquals(LocalDateTime.parse("2018-12-30T19:34:50.63"), found.get().getEndTime());
    }

    @Test// date ห้ามเป็น null
    void B6013394_testReturnsDateMustBeNotNull() throws ParseException {
        // เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        Returns r = new Returns();
        r.setReturnsdate(null);
        r.setNote("test");

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Returns>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Returns> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("returnsdate", v.getPropertyPath().toString());
    }
    
    @Test //ขนาดห้ามเกิน 45
    void B6013394_testNoteNotSizeReturns()  throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        Returns r = new Returns();
        r.setReturnsdate(date);
        r.setNote("12345678901234567890123456789012345678901234565555");

         Set<ConstraintViolation<Returns>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Returns> v = result.iterator().next();
        assertEquals("size must be between 0 and 45", v.getMessage());
        assertEquals("note", v.getPropertyPath().toString());
    }

    @Test //ห้ามใส่สัญลักษณ์ (.+/*)
    void B6013394_testNoteNotpatternReturns() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);

       Returns r = new Returns();
        r.setReturnsdate(date);
        r.setNote("test*");

        Set<ConstraintViolation<Returns>> result = validator.validate(r);
        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Returns> v = result.iterator().next();
        assertEquals("must match \"[a-zA-Z0-9ก-๙-]*\"", v.getMessage());
        assertEquals("note", v.getPropertyPath().toString());
    }


    @Test// note ห้ามเป็น null
    void B6013394_testReturnsnoteMustBeNotNull() throws ParseException {
        //เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        Returns r = new Returns();
        r.setReturnsdate(date);
        r.setNote(null);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Returns>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Returns> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("note", v.getPropertyPath().toString());
    }
}