package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@Table(name="STATUSCUSTOMER")
public class StatusCustomer {
	@Id 
	@SequenceGenerator(name="StatusCus_seq",sequenceName="StatusCus_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="StatusCus_seq")
	@Column(name="Status_ID",unique = true, nullable = true)
	private @NonNull Long id;
	private @NonNull String StatusCus;
	
}