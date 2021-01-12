package com.bookmyshow.bookmyshowapi.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cities")
public class City extends Auditable{

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "State",nullable = false)
    private String state;

    @Column(name = "Country",nullable = false)
    private String country;

}
