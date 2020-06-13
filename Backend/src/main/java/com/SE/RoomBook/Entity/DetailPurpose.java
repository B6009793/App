package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
@NoArgsConstructor
@Table(name = "DetailPurpose")
public class DetailPurpose {
    @Id
    @SequenceGenerator(name = "DetailPurpose_SEQ", sequenceName = "DetailPurpose_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DetailPurpose_SEQ")
    @Column(name = "detailPurpose_id", unique = true, nullable = true)
    private @NonNull Long detailPurpose_id;

    /*
     * @Column(name = "book_name") private @NonNull String name;
     * 
     * @Column(name = "type") private @NonNull String type;
     * 
     * @Column(name = "price") private @NonNull Integer price;
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "Reservations_id")
    @JsonIgnore
    private Reservations reservations;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "PurposeRoom_id")
    @JsonIgnore
    private PurposeRoom purposeRoom;

}