package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ReturnsRepository extends JpaRepository<Returns, Long> {
    Returns findById(long id);

}
