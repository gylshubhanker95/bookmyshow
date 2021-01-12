package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "screens")
public class Screen extends Auditable{

    @ManyToOne
    private Theatre theatre;

    @Column(nullable = false)
    private Long screenNumber;

}
