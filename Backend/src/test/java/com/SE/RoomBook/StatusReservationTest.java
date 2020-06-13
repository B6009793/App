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

@DataJpaTest
public class StatusReservationTest {

    private Validator validator;

    @Autowired
    private StatusReservationRepository statusReservationRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void B6014681_statusReservationOK() {

        StatusReservation s = new StatusReservation();
        s.setStatusReservationName("test");

        statusReservationRepository.saveAndFlush(s);

        Optional<StatusReservation> found = statusReservationRepository.findById(s.getStatusReservation_id());

        assertEquals("test", found.get().getStatusReservationName());

    }

    @Test
    void B6014681_statusMustBeNotNull() {

        StatusReservation s = new StatusReservation();

        s.setStatusReservationName(null);

        Set<ConstraintViolation<StatusReservation>> founderror = validator.validate(s);

        assertEquals(1, founderror.size());

        ConstraintViolation<StatusReservation> result = founderror.iterator().next();

        assertEquals("must not be null", result.getMessage());
        assertEquals("StatusReservationName", result.getPropertyPath().toString());

    }

}