package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.StatusReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StatusReservationRepository extends JpaRepository<StatusReservation, Long> {

    StatusReservation findById(long StatusReservation_id);

    @Query(value = "SELECT * FROM STATUS_RESERVATION  WHERE STATUS_RESERVATION_ID = 1", nativeQuery = true)
    StatusReservation findConfirmById();

    @Query(value = "SELECT * FROM STATUS_RESERVATION  WHERE STATUS_RESERVATION_ID = 2", nativeQuery = true)
    StatusReservation findCancelById();

}
