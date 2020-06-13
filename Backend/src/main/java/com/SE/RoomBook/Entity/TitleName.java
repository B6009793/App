package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@Table(name="TITLENAME")
public class TitleName {
	@Id 
	@SequenceGenerator(name="title_seq",sequenceName="title_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="title_seq")
	@Column(name="Title_ID",unique = true, nullable = true)
	private @NonNull Long id;
	private @NonNull String Title;
	
	
}