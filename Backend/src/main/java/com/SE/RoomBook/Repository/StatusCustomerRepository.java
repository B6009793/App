package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.StatusCustomer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public
interface StatusCustomerRepository extends JpaRepository<StatusCustomer, Long> {
    StatusCustomer findById(long id);
}