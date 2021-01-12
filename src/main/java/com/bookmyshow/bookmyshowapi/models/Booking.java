package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bookings")
public class Booking extends Auditable{

    @ManyToOne
    private User user;

    @OneToOne
    private Ticket ticket;

}
