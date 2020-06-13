package com.SE.RoomBook.Repository;

import com.SE.RoomBook.Entity.Employee;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.data.repository.query.Param;


@CrossOrigin(origins = "http://localhost:8081")
@RepositoryRestResource
public interface EmployeeRepository extends JpaRepository <Employee, Long>{
    Employee findById(long id);
    @Query( value = "SELECT * FROM Employee e where e.em_name = :em_name and e.password = :password",
            nativeQuery = true)
            Employee findPatientByEmployee(@Param("em_name") String em_name,
            @Param("password") String password);
}