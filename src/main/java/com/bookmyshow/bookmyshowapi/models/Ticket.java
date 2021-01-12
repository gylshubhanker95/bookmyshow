package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "ticket")
public class Ticket extends Auditable{

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Screen screen;

    private Double amount;

    private Date showTime;

    private String tierName;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bookedAt",nullable = false)
    private Date bookedAt;

    @ElementCollection
    @Column(name = "seats")
    private List<String> seats = new ArrayList<>();



}
