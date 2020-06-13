package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.Room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public
interface RoomRepository extends JpaRepository<Room, Long> {
    Room findById(long id);

    //repository เป็นตัวติดต่อกับดาต้าเบส
}