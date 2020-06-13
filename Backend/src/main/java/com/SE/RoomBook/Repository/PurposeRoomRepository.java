package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.PurposeRoom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PurposeRoomRepository extends JpaRepository<PurposeRoom, Long> {
    PurposeRoom findById(long id);
}