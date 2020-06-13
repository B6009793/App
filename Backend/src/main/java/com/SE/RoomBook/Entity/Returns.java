package com.SE.RoomBook.Entity;

import lombok.*;
import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.SE.RoomBook.Entity.*;

@Data
@Entity
@Table(name = "RETURNS")
public class Returns{
    @Id
    @SequenceGenerator(name = "RETURNS_SEQ", sequenceName = "RETURNS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RETURNS_SEQ")
    
    @Column(name = "Returns_id", unique = true, nullable = true)
    private Long id;

    @NotNull
    @Column(name = "Returnsdate")
    private Date returnsdate;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9ก-๙-]*")
    @Size(min=0, max=45)
    @Column(name="NOTE")
    private String note;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
    @JoinColumn(name = "Customer_id", insertable = true)
    @JsonManagedReference
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Employee.class)
    @JoinColumn(name = "Emp_id", insertable = true)
    @JsonManagedReference
    private  Employee employee;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = Borrow.class)
    @JoinColumn(name = "Borrow_id", insertable = true)
    @JsonManagedReference
    private Borrow borrow;
}
