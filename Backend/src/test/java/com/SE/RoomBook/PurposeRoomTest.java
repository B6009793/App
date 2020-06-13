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
/**
 * PurposeRoomTest
 */
public class PurposeRoomTest {
    private Validator validator;

    @Autowired
    private PurposeRoomRepository purposeRoomRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void B6014681_testFullFieldDataPurposeRoom() {
        PurposeRoom pRoom = new PurposeRoom();
        pRoom.setName("ทดสอบ");
        purposeRoomRepository.saveAndFlush(pRoom);

        Optional<PurposeRoom> found = purposeRoomRepository.findById(pRoom.getPurposeRoom_id());
        assertEquals("ทดสอบ", found.get().getName());

    }

    @Test
    void B6014681_testNotThaiCharactersFieldNameDataPurposeRoom() {
        PurposeRoom room = new PurposeRoom();
        room.setName("TestNotThaiCharacters");

        Set<ConstraintViolation<PurposeRoom>> result = validator.validate(room);

        assertEquals(1, result.size());

        ConstraintViolation<PurposeRoom> v = result.iterator().next();
        assertEquals("must match \"[\\u0E00-\\u0E7F|\\u0020]+\"", v.getMessage());
        assertEquals("name", v.getPropertyPath().toString());

    }

    @Test
    void B6014681_testNameMustNotBe41CharactersPurposeRoom() {
        PurposeRoom room = new PurposeRoom();
        room.setName("ฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟฟ");

        Set<ConstraintViolation<PurposeRoom>> result = validator.validate(room);

        assertEquals(1, result.size());

        ConstraintViolation<PurposeRoom> v = result.iterator().next();
        assertEquals("size must be between 3 and 40", v.getMessage());
        assertEquals("name", v.getPropertyPath().toString());

    }

    @Test
    void B6014681_testNameMustNotBe2CharactersPurposeRoom() {
        PurposeRoom room = new PurposeRoom();
        room.setName("ฟฟ");

        Set<ConstraintViolation<PurposeRoom>> result = validator.validate(room);

        assertEquals(1, result.size());

        ConstraintViolation<PurposeRoom> v = result.iterator().next();
        assertEquals("size must be between 3 and 40", v.getMessage());
        assertEquals("name", v.getPropertyPath().toString());

    }

    @Test
    void B6014681_testNameMustNotBeDigitsPurposeRoom() {
        PurposeRoom room = new PurposeRoom();
        room.setName("1234567890");

        Set<ConstraintViolation<PurposeRoom>> result = validator.validate(room);

        assertEquals(1, result.size());

        ConstraintViolation<PurposeRoom> v = result.iterator().next();
        assertEquals("must match \"[\\u0E00-\\u0E7F|\\u0020]+\"", v.getMessage());
        assertEquals("name", v.getPropertyPath().toString());

    }


    @Test
    void B6014681_testNameMustBeUnique() {
        // สร้าง person object
        PurposeRoom room = new PurposeRoom();
        room.setName("ทดสอบฟหก");
        purposeRoomRepository.saveAndFlush(room);

        // คาดหวังว่า DataIntegrityViolationException จะถูก throw
        assertThrows(DataIntegrityViolationException.class, () -> {
            // สร้าง person object ตัวที่ 2
            PurposeRoom room2 = new PurposeRoom();
            room2.setName("ทดสอบฟหก");
            purposeRoomRepository.saveAndFlush(room2);
        });
    }
}