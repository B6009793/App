package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.Status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public
interface StatusRepository extends JpaRepository<Status, Long> {
	Status findById(long Status_id);
}
