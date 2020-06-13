package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.validation.constraints.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.SE.RoomBook.Entity.TitleName;
import com.SE.RoomBook.Entity.Gender;
import com.SE.RoomBook.Entity.StatusCustomer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Data
@Entity
@NoArgsConstructor
@Table(name="CUSTOMER" ,uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Customer_Email"}) })
public class Customer {
	@Id
	@SequenceGenerator(name="customer_seq",sequenceName="customer_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_seq")
	@Column(name="Cus_id",unique = true, nullable = true)
    private @NonNull Long id;

    @NotNull
    @Column(name="Customer_name")
    private String Name;

    @NotNull
    private String Password;

    @NotNull
    @Email
    @Column(name="Customer_Email",unique = true)
    private String Email;

    @NotNull
    @Pattern(regexp = "\\d{10}")
    @Column(name="Customer_Phone")
    private String Phone;



	@ManyToOne(fetch = FetchType.EAGER ,targetEntity = Gender.class)
	@JoinColumn(name="Gender",insertable = true)
    private Gender Genders;

    @ManyToOne(fetch = FetchType.EAGER ,targetEntity = StatusCustomer.class)
	@JoinColumn(name="StatusCus",insertable = true)
    private StatusCustomer Statuss;

    @ManyToOne(fetch = FetchType.EAGER ,targetEntity = TitleName.class)
	@JoinColumn(name="Title",insertable = true)
    private TitleName TitleNames;


    

	}
