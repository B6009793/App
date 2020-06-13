package com.SE.RoomBook.model;

import java.sql.Date;

import lombok.*;

// model for  request body
@Data
public class BodyBook {
    private Long customerid;
    private Long roomid;
    private Long[] purosebook;
    private String start;
    private String end;
}
