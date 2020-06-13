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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class BorrowTest {

    Validator Validator;

    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    ManageEquipmentRepository manageEquipmentRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    GenderRepository genderRepository;
    @Autowired
    StatusCustomerRepository statusCustomerRepository;
    @Autowired
    TitleNameRepository titleNameRepository;
    @Autowired
    EquipmentNameRepository equipmentNameRepository;
    @Autowired
    EquipmentTypeRepository equipmentTypeRepository;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator = factory.getValidator();
    }

    // test1
    @Test
    void B6007317_testAllOK() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("test");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        borrow = borrowRepository.saveAndFlush(borrow);

        Borrow found = borrowRepository.findById(borrow.getBid()).get();

        assertEquals(date, found.getBordate());
        assertEquals("test", found.getBornote());
        assertEquals(true, found.getBorrowStatus());
        assertEquals(customer, found.getCustomer());
        assertEquals(employee, found.getEmployee());
        assertEquals(manageEquipment, found.getManageequipment());

    }

    @Test
    void B6007317_testDateMustBeNotNull() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(null);
        borrow.setBornote("test");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("bordate", v.getPropertyPath().toString());

    }

    @Test
    void B6007317_testNoteMax50Digits() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("teststeststeststeststeststeststeststeststeststeststests");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("size must be between 0 and 50", v.getMessage());
        assertEquals("bornote", v.getPropertyPath().toString());
    }

    @Test
    void B6007317_testNotePattern() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("test++");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must match \"[a-zA-Z0-9ก-๙-]*\"", v.getMessage());
        assertEquals("bornote", v.getPropertyPath().toString());
    }

    @Test
    void B6007317_testBorrow_noteMustBeNotNull() {
        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote(null);
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("bornote", v.getPropertyPath().toString());

    }

    @Test
    void B6007317_testBorrow_statusNotNull() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("test");
        borrow.setBorrowStatus(null);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("borrowStatus", v.getPropertyPath().toString());

    }

    @Test
    void B6007317_testEmployeeMustNotBeNull() {

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(null);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("test");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(null);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("employee", v.getPropertyPath().toString());

    }

    @Test
    void B6007317_testCustomerMustNotBeNull() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        EquipmentName equipmentName = new EquipmentName();
        equipmentName.setName("equipmentName");
        equipmentName = equipmentNameRepository.saveAndFlush(equipmentName);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setType("equipmentType");
        equipmentType = equipmentTypeRepository.saveAndFlush(equipmentType);

        ManageEquipment manageEquipment = new ManageEquipment();
        manageEquipment.setManageEquipment_amount(5);
        manageEquipment.setEquipmentType(equipmentType);
        manageEquipment.setEquipmentName(equipmentName);
        manageEquipment.setEmployee(employee);
        manageEquipment = manageEquipmentRepository.saveAndFlush(manageEquipment);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("test");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(null);
        borrow.setEmployee(employee);
        borrow.setManageequipment(manageEquipment);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("customer", v.getPropertyPath().toString());

    }

    @Test
    void B6007317_testManegeEquipmentNotNull() {

        Employee employee = new Employee();
        employee.setEm_name("employeetest");
        employee.setPassword("employeetest1234");
        employee = employeeRepository.saveAndFlush(employee);

        Gender gender = new Gender();
        gender.setGender("gender");
        gender = genderRepository.saveAndFlush(gender);

        StatusCustomer statusCustomer = new StatusCustomer();
        statusCustomer.setStatusCus("statusCustomer");
        statusCustomer = statusCustomerRepository.saveAndFlush(statusCustomer);

        TitleName titleName = new TitleName();
        titleName.setTitle("titleName");
        titleName = titleNameRepository.saveAndFlush(titleName);

        Customer customer = new Customer();
        customer.setName("customertest");
        customer.setPassword("customertest1234");
        customer.setEmail("customertest@gmail.com");
        customer.setPhone("0812345678");
        customer.setGenders(gender);
        customer.setStatuss(statusCustomer);
        customer.setTitleNames(titleName);
        customer = customerRepository.saveAndFlush(customer);

        Borrow borrow = new Borrow();
        Date date = new Date();

        borrow.setBordate(date);
        borrow.setBornote("test");
        borrow.setBorrowStatus(true);
        borrow.setCustomer(customer);
        borrow.setEmployee(employee);
        borrow.setManageequipment(null);

        // ตรวจสอบ error และเก็บค่า error ในรูปแบบ set
        Set<ConstraintViolation<Borrow>> result = Validator.validate(borrow);

        // result ต้องมี error 1 ค่าเท่านั้น
        assertEquals(1, result.size());

        // error message ตรงชนิด และถูก field
        ConstraintViolation<Borrow> v = result.iterator().next();
        assertEquals("must not be null", v.getMessage());
        assertEquals("manageequipment", v.getPropertyPath().toString());

    }
}
