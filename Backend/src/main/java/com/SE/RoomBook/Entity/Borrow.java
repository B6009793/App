package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "BORROW")
public class Borrow {
    @Id
    @SequenceGenerator(name = "borrow_seq", sequenceName = "borrow_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "borrow_seq")
    @Column(name = "borrow__id", unique = true, nullable = true)
    private @NonNull Long bid;

    @NotNull
    @Column(name = "borrow_date")
    private Date bordate;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9ก-๙-]*")
    @Size(min = 0, max = 50)
    @Column(name = "borrow_note")
    private String bornote;

    @NotNull
    @Column(name = "borrow_Status")
    private Boolean borrowStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Employee.class)
    @JoinColumn(name = "EMPLOYEE_ID", insertable = true)
    private Employee employee;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Customer.class)
    @JoinColumn(name = "CUSTOMER_ID", insertable = true)
    private Customer customer;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = ManageEquipment.class)
    @JoinColumn(name = "ManageEquipment_ID", insertable = true)
    private ManageEquipment manageequipment;

}
