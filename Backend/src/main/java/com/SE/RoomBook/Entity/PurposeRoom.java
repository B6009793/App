package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Data
@Entity
@NoArgsConstructor
@Table(name = "PurposeRoom")
public class PurposeRoom {

    @Id
    @SequenceGenerator(name = "PurposeRoom_seq", sequenceName = "PurposeRoom_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PurposeRoom_seq")
    @Column(name = "PurposeRoom_id", unique = true, nullable = true)
    private @NonNull Long PurposeRoom_id;

    @Pattern(regexp = "[\\u0E00-\\u0E7F|\\u0020]+") // \\u0020
    @NotNull
    @Size( min = 3 ,max=40)
    @Column(name = "Name", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    // , mappedBy = "purposeRoom"
    private Collection<DetailPurpose> detailPurpose;
}
