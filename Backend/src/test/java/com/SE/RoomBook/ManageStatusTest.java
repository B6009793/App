package com.SE.RoomBook;

import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

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
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
public class ManageStatusTest {
    private Validator validator;

    @Autowired
    private ManageStatusRepository managestatusRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    //-----------------------ใส่ข้อมูลถูกต้อง---------------------------------
    @Test
    void B6002688_testManageStatusOK() throws ParseException {
        ManageStatus r = new ManageStatus();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        
        r.setCreate_at(date);
        r.setDelete_at(null);
        r.setManageStatus_note("test");
    

        r = managestatusRepository.saveAndFlush(r);
        Optional<ManageStatus> found = managestatusRepository.findById(r.getManageStatus_id());

        System.out.println(found.get().getCreate_at());
        System.out.println(found.get().getDelete_at());
        // ค่าออกมาจะต้องได้ตัวเดิม
        assertEquals(date, found.get().getCreate_at());
        assertEquals(null, found.get().getDelete_at());
        // เพื่อให้แน่ใจว่าค่าที่ออกมาจะต้องเป็นตัวเดิม
  
        assertEquals(sdf.parse("31-08-1982 10:20:56"), found.get().getCreate_at());
        assertEquals(null, found.get().getDelete_at());
        assertEquals("test", found.get().getManageStatus_note());
        
    }

    //--------------Creat_at ห้ามเป็น null-------------
    @Test
    void B6002688_testCreate_atManageStatusMustBeNotNull() throws ParseException {
        // //เตรียมเวลา ทั้ง Date และ LocalDate
        // SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        // String dateInString = "31-08-1982 10:20:56";
        // Date date = sdf.parse(dateInString);
        // // สร้าง ManageStatus และทำการใส่ เวลา (Date and LocalDate)
        ManageStatus r = new ManageStatus();
        r.setCreate_at(null);
        r.setDelete_at(null);
        r.setManageStatus_note("test");

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<ManageStatus>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<ManageStatus> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("Create_at", v.getPropertyPath().toString());

    }

    //----------ManageStatus_note ห้ามใส่ข้อมูลเกิน 50 ตัว ----------
    @Test
    void B6002688_testNoteSize()  throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        ManageStatus r = new ManageStatus();
        r.setCreate_at(date);
        r.setDelete_at(null);
        r.setManageStatus_note("testttttttttttttttttttttttttttttttttttttttttttttttttt");

         Set<ConstraintViolation<ManageStatus>> result = validator.validate(r);

        // // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<ManageStatus> v = result.iterator().next();
        assertEquals("size must be between 0 and 50", v.getMessage());
        assertEquals("ManageStatus_note", v.getPropertyPath().toString());
    }

    //----------ใส่ข้อมูลในรูปแบบที่ต้องการ ห้ามใส่สัญลักษณ์ต่างๆ ใ่ส่ - ได้--------------
    @Test
    void B6002688_testNotePattern() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);

        ManageStatus r = new ManageStatus();
        r.setCreate_at(date);
        r.setDelete_at(null);

        r.setManageStatus_note("ไฟดับ+");

        Set<ConstraintViolation<ManageStatus>> result = validator.validate(r);
        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<ManageStatus> v = result.iterator().next();
        assertEquals("must match \"[a-zA-Z0-9ก-๙-]*\"", v.getMessage());
        assertEquals("ManageStatus_note", v.getPropertyPath().toString());
    }
   
   //--------------Creat_at ห้ามเป็น null-------------
    @Test
    void B6002688_testManageStatus_noteMustBeNotNull() throws ParseException {
        // //เตรียมเวลา ทั้ง Date และ LocalDate
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        String dateInString = "31-08-1982 10:20:56";
        Date date = sdf.parse(dateInString);
        // // สร้าง ManageStatus และทำการใส่ เวลา (Date and LocalDate)
        ManageStatus r = new ManageStatus();
        r.setCreate_at(date);
        r.setDelete_at(null);
        r.setManageStatus_note(null);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<ManageStatus>> result = validator.validate(r);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<ManageStatus> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("ManageStatus_note", v.getPropertyPath().toString());

    }


}