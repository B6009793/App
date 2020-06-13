package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.DetailPurpose;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DetailPurposeRepository extends JpaRepository<DetailPurpose, Long> {
    DetailPurpose findById(long id);
}