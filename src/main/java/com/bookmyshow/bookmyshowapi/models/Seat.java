package com.bookmyshow.bookmyshowapi.models;

import jdk.jfr.BooleanFlag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "seats")
public class Seat extends Auditable{

    @ManyToOne
    private Tier tier;

    @Column(name = "seatName",nullable = false)
    private String seatName;

    @Column(name = "isBooked",nullable = false)
    private boolean isBooked;

    @ManyToOne
    private Booking booking;

    private double seatPrice;

}
