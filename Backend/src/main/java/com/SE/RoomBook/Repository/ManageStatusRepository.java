package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.ManageStatus;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
@CrossOrigin(origins = "http://localhost:8080")
@RepositoryRestResource
public
interface ManageStatusRepository extends JpaRepository<ManageStatus, Long> {
    
  @Query(value = "SELECT * FROM MANAGE_STATUS where DELETE_AT is null and room_id = :Room_id",
            nativeQuery = true)
  ManageStatus findRoomNull(@Param("Room_id") Long i);


  @Query(value = "SELECT * FROM MANAGE_STATUS where DELETE_AT is null and status_id = 1",
            nativeQuery = true)
  Collection<ManageStatus> findSellectRoomOnline();

 @Query(value = "SELECT * FROM MANAGE_STATUS where DELETE_AT is null ",
            nativeQuery = true)
  Collection<ManageStatus> findSellectStatusRoom();

  ManageStatus findById(long id);
}
//  ManageStatus findByRoom_id(Long Room_id);
//         Collection<ManageStatus> findAll(Long Room_id);
//         Long Count(); 

