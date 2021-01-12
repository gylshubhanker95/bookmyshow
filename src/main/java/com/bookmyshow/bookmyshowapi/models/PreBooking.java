package com.bookmyshow.bookmyshowapi.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "prebooking")
public class PreBooking extends Auditable{

    @ManyToOne
    private User user;
    @OneToOne
    private Seat seat;
    @ManyToOne
    private Show show;

}
