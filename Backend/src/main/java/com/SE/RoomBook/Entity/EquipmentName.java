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
@Table(name = "EquipmentName")
public class EquipmentName {
    @Id
    @SequenceGenerator(name = "EquipmentName_SEQ", sequenceName = "EquipmentName_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EquipmentName_SEQ")
    @Column(name="EquipmentName_ID",unique = true, nullable = true)
    private @NonNull Long equipmentname_id;

    @Column(name="EQname")
    private @NonNull String name;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<ManageEquipment> ManageEquipment;

}
