package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
@NoArgsConstructor
@Table(name="STATUS")
public class Status {
    @Id
    @SequenceGenerator(name="Status_SEQ",sequenceName="Status_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Status_SEQ")
    @Column(name="Status_id",unique = true, nullable = true)
    private @NonNull Long Status_id;
    @Column(name="Status_name")
    private @NonNull String Status_name;

    //@OneToMany(fetch = FetchType.EAGER)
    //mappedBy  = "Status"
    //private Collection<ManageStatus> ManageStatus;
}

