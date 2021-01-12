package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Show extends Auditable{

    @ManyToOne
    private Movie movie;

    @DateTimeFormat(pattern = "yyyy/MM/dd,HH-mm-ss")
    private Date showTime;

    @ManyToOne
    private Screen screen;

    @ManyToOne
    private City city;

}
