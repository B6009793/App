package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.TitleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public
interface TitleNameRepository extends JpaRepository<TitleName, Long> {
    TitleName findById(long id);
}