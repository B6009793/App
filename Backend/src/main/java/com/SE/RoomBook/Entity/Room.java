package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import java.util.Collection;
import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Data
@Entity
@NoArgsConstructor
@Table(name="ROOMS")
public class Room {
    @Id
    @SequenceGenerator(name="Room_seq",sequenceName="Room_seq")               
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Room_seq")  
    @Column(name = "Room_id", unique = true, nullable = true)
    private @NonNull Long Room_id;
    @Column(name = "Room_name")
    private @NonNull String Room_name;


    @OneToMany(fetch = FetchType.EAGER)
    // mappedBy  = "Room"
    private Set<ManageStatus> ManageStatus;
}
