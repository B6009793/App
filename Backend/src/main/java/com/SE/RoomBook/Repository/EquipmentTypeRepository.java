package com.SE.RoomBook.Repository;
import com.SE.RoomBook.Entity.EquipmentType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType, Long> {
	EquipmentType findById(long equipmenttype_id);
}
