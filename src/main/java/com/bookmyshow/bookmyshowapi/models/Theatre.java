package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "theatres")
public class Theatre extends Auditable{

    @ManyToOne
    private City city;

    @Column(name = "theatreName",nullable = false)
    private String name;

    @Column(nullable = false)
    private Double basePrice;

    @Column(nullable = false)
    private String locationName;

    @ManyToOne
    private Location location;
}
