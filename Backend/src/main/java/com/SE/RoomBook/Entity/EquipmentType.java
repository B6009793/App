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
@Table(name = "EquipmentType")
public class EquipmentType {
    @Id
    @SequenceGenerator(name = "Equipmenttype_SEQ", sequenceName = "equipmenttype_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipmenttype_SEQ")
    @Column(name="EquipmentType_ID",unique = true, nullable = true)
    private @NonNull Long equipmenttype_id;

    @Column(name="Typename")
    private @NonNull String type;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<ManageEquipment> ManageEquipment;
}
