package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Reservations")
public class Reservations {
    @Id
    @SequenceGenerator(name = "Reservations_SEQ", sequenceName = "Reservations_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Reservations_SEQ")
    @Column(name = "Reservations_id", unique = true, nullable = true)
    private @NonNull Long Reservations_id;

    @NotNull
    @Future
    @Column(name = "StartTime")
    private LocalDateTime StartTime;

    @NotNull
    @Future
    @Column(name = "EndTime")
    private LocalDateTime EndTime;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ManageStatus.class)
    @JoinColumn(name = "manageStatus_id", insertable = true)
    private ManageStatus manageStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "reservations")
    private List<DetailPurpose> detailPurpose;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
    @JoinColumn(name = "Cus_id", insertable = true)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = StatusReservation.class)
    @JoinColumn(name = "StatusReservation_id", insertable = true)
    private StatusReservation statusReservation;

    @NotNull
    @Column(name = "Date")
    private Date bookdate;

}