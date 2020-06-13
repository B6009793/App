package com.SE.RoomBook.Repository;

import java.util.Collection;
import java.util.Optional;

import com.SE.RoomBook.Entity.Reservations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReservationsRepository extends JpaRepository<Reservations, Long> {
	Reservations findById(long id);

	@Query(value = "SELECT * FROM RESERVATIONS WHERE STATUS_RESERVATION_ID = 1 AND MANAGE_STATUS_ID = :idroom AND (( START_TIME = :start  OR  END_TIME =  :end )  OR (START_TIME > :start  AND  START_TIME <  :end)  OR  (START_TIME > :start  AND  END_TIME <  :end)  OR (START_TIME < :start  AND  END_TIME >  :end) OR  (END_TIME > :start  AND  END_TIME <  :end))", nativeQuery = true)
	Reservations findTime(@Param("start") String start, @Param("end") String end, @Param("idroom") Long idroom);

	@Query(value = "SELECT * FROM RESERVATIONS WHERE STATUS_RESERVATION_ID = 1 AND RESERVATIONS_ID = :id", nativeQuery = true)
	Reservations findReservationById(@Param("id") Long id);

	@Query(value = "SELECT * FROM RESERVATIONS WHERE STATUS_RESERVATION_ID = 1 ", nativeQuery = true)
	Collection<Reservations> findByReservationAll();

	@Query(value = "SELECT * FROM RESERVATIONS WHERE STATUS_RESERVATION_ID = 1  AND 	CUS_ID =:id AND Start_time > CURDATE() ", nativeQuery = true)
	Collection<Reservations> findCustomerofReservationByIdAlllist(@Param("id") Long id);
}