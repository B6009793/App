package com.SE.RoomBook;

import com.SE.RoomBook.Entity.*;
import com.SE.RoomBook.Repository.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class RoomBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomBookApplication.class, args);
	}

	@Bean
	ApplicationRunner init(GenderRepository genderRepository, TitleNameRepository titleNameRepository,
			StatusCustomerRepository statusCustomer, EmployeeRepository employeeRepository,
			PurposeRoomRepository purposeRoomRepository, RoomRepository roomRepository,
			StatusRepository statusRepository, EquipmentTypeRepository EquipmentTypeRepository,
			EquipmentNameRepository EquipmentNameRepository, StatusReservationRepository statusReservationRepository) {
		return args -> {
			Employee em1 = new Employee();
			em1.setEm_name("Dodo");
			em1.setPassword("1234");
			employeeRepository.save(em1);

			Employee em2 = new Employee();
			em2.setEm_name("Mook");
			em2.setPassword("5678");
			employeeRepository.save(em2);

			Employee em3 = new Employee();
			em3.setEm_name("Prow");
			em3.setPassword("9876");
			employeeRepository.save(em3);

			Employee em4 = new Employee();
			em4.setEm_name("Dodo");
			em4.setPassword("4444");
			employeeRepository.save(em4);

			Stream.of("Man", "Female").forEach(Gender -> {
				Gender gender = new Gender(); // สร้าง Object Gender
				gender.setGender(Gender); // set ชื่อ (Gender) ให้ Object ชื่อ gender
				genderRepository.save(gender); // บันทึก Objcet ชื่อ Gender
			});

			Stream.of("Mr.", "Miss.", "Professor.", "Dr.").forEach(Title -> {
				TitleName title = new TitleName(); // สร้าง Object Title
				title.setTitle(Title); // set ชื่อ (Title) ให้ Object ชื่อ title
				titleNameRepository.save(title); // บันทึก Objcet ชื่อ Title
			});

			Stream.of("Studens", "Teacher", "Normal Person").forEach(Status -> {
				StatusCustomer statuscus = new StatusCustomer(); // สร้าง Object Status
				statuscus.setStatusCus(Status); // set ชื่อ (StatusCustomer) ให้ Object ชื่อ Status
				statusCustomer.save(statuscus); // บันทึก Objcet ชื่อ Status
			});

			Stream.of("อ่านหนังสือ ทบทวนบทเรียน", "ทำการบ้าน รายงาน", "ติว", "ประชุม").forEach(name -> {
				PurposeRoom p = new PurposeRoom(); // สร้าง Object Type
				p.setName(name);
				purposeRoomRepository.save(p); // บันทึก Objcet ชื่อ Type
			});

			Stream.of("G01", "G02", "G03", "S01", "S02", "S03", "P01", "P02").forEach(name -> {
				Room room = new Room(); // สร้าง Object
				room.setRoom_name(name); // set ชื่อ (name) ให้ Object
				roomRepository.save(room); // บันทึก Objcet
			});

			Stream.of("ออนไลน์", "ออฟไลน์").forEach(name -> {
				Status status = new Status(); // สร้าง Object
				status.setStatus_name(name); // set ชื่อ (name) ให้ Object
				statusRepository.save(status); // บันทึก Objcet
			});

			Stream.of("Stationary", "Electronic").forEach(type -> {
				EquipmentType EquipmentType = new EquipmentType();
				EquipmentType.setType(type);
				EquipmentTypeRepository.save(EquipmentType);
			});

			Stream.of("Ipad", "Writeboard pen", "Plug", "Headphones").forEach(name -> {
				EquipmentName EquipmentName = new EquipmentName();
				EquipmentName.setName(name);
				EquipmentNameRepository.save(EquipmentName);
			});
			// confirm , cancel

			Stream.of("confirm", "cancel").forEach(name -> {
				StatusReservation SR = new StatusReservation();
				SR.setStatusReservationName(name);
				statusReservationRepository.save(SR);
			});

		};
	}

}
