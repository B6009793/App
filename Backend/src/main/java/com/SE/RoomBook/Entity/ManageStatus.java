package com.SE.RoomBook.Entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import com.SE.RoomBook.Entity.Room;
import com.SE.RoomBook.Entity.Status;
import com.SE.RoomBook.Entity.Employee;

import javax.validation.constraints.*;

import javax.persistence.*; // เพื่อใช้ @NamedQuery

@Data
@Entity

// query หา id ที่มากที่สุด ถ้าไม่มีจะ return null ---------------------------

// @NamedQuery(name = "ManageStatus.Count",
//            query = "SELECT MAX(ManageStatus_id) FROM ManageStatus")

// @NamedQuery(name = "ManageStatus.Count",
//     query = "SELECT * FROM MANAGE_STATUS where DELETE_AT is null")

@NoArgsConstructor
@Table(name="ManageStatus")

public class ManageStatus {
    @Id
    @SequenceGenerator(name="ManageStatus_seq",sequenceName="ManageStatus_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ManageStatus_seq")
    @Column(name = "ManageStatus_id", unique = true, nullable = true)
    private @NonNull Long ManageStatus_id;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Status.class)
    @JoinColumn(name = "Status_id", insertable = true)
    private Status status;


    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Room.class)
    @JoinColumn(name = "Room_id", insertable = true)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Employee.class)
    @JoinColumn(name = "EMPLOYEE_ID", insertable = true)
    private Employee employee;

    @NotNull
    @Column(name="CREATE_AT")
    private Date Create_at;

    @Column(name="DELETE_AT")
    private Date Delete_at;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9ก-๙-]*")
    @Size(min=0, max=50)
    @Column(name="NOTE")
    private String ManageStatus_note;

}
