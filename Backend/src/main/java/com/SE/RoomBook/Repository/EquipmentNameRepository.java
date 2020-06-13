package com.SE.RoomBook.Repository;
import com.SE.RoomBook.Entity.EquipmentName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EquipmentNameRepository extends JpaRepository<EquipmentName, Long> {
	EquipmentName findById(long equipmentname_id);
}
